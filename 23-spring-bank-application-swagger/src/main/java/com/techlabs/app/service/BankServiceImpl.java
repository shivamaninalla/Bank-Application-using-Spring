package com.techlabs.app.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;
import com.techlabs.app.dto.AccountRequestDto;
import com.techlabs.app.dto.AccountResponseDto;
import com.techlabs.app.dto.BankRequestDto;
import com.techlabs.app.dto.CustomerRequestDto;
import com.techlabs.app.dto.CustomerResponseDto;
import com.techlabs.app.dto.ProfileRequestDto;
import com.techlabs.app.dto.TransactionResponseDto;
import com.techlabs.app.dto.UserResponseDto;
import com.techlabs.app.entity.Account;
import com.techlabs.app.entity.Bank;
import com.techlabs.app.entity.Customer;
import com.techlabs.app.entity.Transaction;
import com.techlabs.app.entity.TransactionType;
import com.techlabs.app.entity.User;
import com.techlabs.app.exception.CustomerNotFoundException;
import com.techlabs.app.exception.NoRecordFoundException;
import com.techlabs.app.repository.AccountRepository;
import com.techlabs.app.repository.BankRepository;
import com.techlabs.app.repository.CustomerRepository;
import com.techlabs.app.repository.TransactionRepository;
import com.techlabs.app.repository.UserRepository;
import com.techlabs.app.util.EmailUtil;
import com.techlabs.app.util.MailStructure;
import com.techlabs.app.util.PDFUtil;
import com.techlabs.app.util.PagedResponse;

import jakarta.mail.MessagingException;


@Service
public class BankServiceImpl implements BankService {
	CustomerRepository customerRepository;

	AccountRepository accountRepository;
	BankRepository bankRepository;
	TransactionRepository transactionRepository;
	UserRepository userRepository;
	PasswordEncoder passwordEncoder;

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private PDFUtil pdfUtil=new PDFUtil();
	
	@Autowired
	private EmailUtil emailUtil;

	@Value("$(spring.mail.username)")
	private String fromMail;

	public BankServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository,
			BankRepository bankRepository, TransactionRepository transactionRepository, UserRepository userRepository,
			PasswordEncoder passwordEncoder) {
		super();
		this.customerRepository = customerRepository;
		this.accountRepository = accountRepository;
		this.bankRepository = bankRepository;
		this.transactionRepository = transactionRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public PagedResponse<CustomerResponseDto> getAllCustomers(int page, int size, String sortBy, String direction) {
		Sort sort = Sort.by(sortBy);
		if (direction.equalsIgnoreCase(Sort.Direction.DESC.name())) {
			sort = sort.descending();
		} else {
			sort = sort.ascending();
		}
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Customer> customerPage = customerRepository.findAll(pageable);

		List<CustomerResponseDto> customerDto = convertCustomertoCustomerDto(customerPage.getContent());
		return new PagedResponse<>(customerDto, customerPage.getNumber(), customerPage.getSize(),
				customerPage.getTotalElements(), customerPage.getTotalPages(), customerPage.isLast());
	}

	private List<CustomerResponseDto> convertCustomertoCustomerDto(List<Customer> all) {
		List<CustomerResponseDto> customers = new ArrayList<>();

		for (Customer i : all) {
			CustomerResponseDto customer = new CustomerResponseDto();
			customer.setCustomer_id(i.getCustomer_id());
			customer.setFirstName(i.getFirstName());
			customer.setLastName(i.getLastName());
			customer.setTotalBalance(i.getTotalBalance());
			customer.setAccounts(convertAccounttoAccountResponseDto(i.getAccounts()));
			customers.add(customer);
		}
		return customers;
	}

	private List<AccountResponseDto> convertAccounttoAccountResponseDto(List<Account> accounts) {

		List<AccountResponseDto> accountResponseDto = new ArrayList<>();
		for (Account account : accounts) {

			accountResponseDto.add(convertAccounttoAccountResponseDto(account));

		}
		return accountResponseDto;
	}

	private AccountResponseDto convertAccounttoAccountResponseDto(Account account) {

		AccountResponseDto accountResponseDto = new AccountResponseDto();
		accountResponseDto.setAccountNumber(account.getAccountNumber());
		accountResponseDto.setBalance(account.getBalance());
		accountResponseDto.setActive(account.isActive());
		return accountResponseDto;
	}



	@Override
	public CustomerResponseDto findCustomerByid(long id) {
		Customer customer = customerRepository.findById(id).orElse(null);
		if (customer == null) {
			throw new CustomerNotFoundException("customer not found with id: " + id);
		} else {
			return convertCustomerToCustomerResponseDto(customer);
		}
	}

	@Override
	public CustomerResponseDto addAccount(long cid,int bid) {
		
		 Account account = new Account();
		 Bank bank = bankRepository.findById(bid).orElse(null);
		
		 
		 if(bank != null) {
			 Customer customer = customerRepository.findById(cid).orElse(null);
			 if(!customer.isActive()) {
					throw new NoRecordFoundException("Customer is not activated "+customer.getCustomer_id());
				}
			 
			 if(customer != null){
				 
				
				 account.setBalance(1000);
				 account.setBank(bank);
				 account.setCustomer(customer);
				 
				 customer.addbankAccount(account);
				 double total_salary=1000;
				 if(accountRepository.getTotalBalance(customer)!=0) {
					 total_salary+=accountRepository.getTotalBalance(customer);
				 }
				 customer.setTotalBalance(total_salary);
				 Customer save = customerRepository.save(customer);
				 User user = customer.getUser();
				 String subject = "Your Account Has Been Successfully Created at SBI !";
				 		String emailBody = "Dear " + customer.getFirstName() + " " + customer.getLastName() + ",\n\n"
				 		    + "We are pleased to inform you that your account has been successfully created with us!\n\n"
				 		    + "Your Customer ID is " + customer.getCustomer_id() + ". You can view all the details of your account by logging into our application and sending a request to get customer details by your ID.\n\n"
				 		    + "If you need any assistance or have any questions, our support team is ready to help. We are committed to providing you with the best banking experience.\n\n"
				 		    + "Thank you for choosing SBI. We look forward to supporting your financial needs.\n\n"
				 		    + "Best regards,\n"
				 		    + "Customer Relations Team\n"
				 		    + "SBI";

				 		sendEmail(user.getEmail(), subject,emailBody);
				 return convertCustomerToCustomerResponseDto(save);
				 
			 }
			 else {
				 throw new CustomerNotFoundException("Customer with this id "+cid+" not found");
			 }
		 }
		return null;
		 
	
	
	}

	private Account convertAcountResponseDtoToAccount(AccountRequestDto accountRequestDto) {
		Account account = new Account();
		account.setAccountNumber(accountRequestDto.getAccountNumber());
		account.setBalance(accountRequestDto.getBalance());
		account.setBank(convertBankDtotoBank(accountRequestDto.getBankrequestDto()));
		account.setCustomer(convertcustomerDtoToCustomer(accountRequestDto.getCustomerRequestDto()));
		return account;

	}

	private Customer convertcustomerDtoToCustomer(CustomerRequestDto customerRequestDto) {

		Customer customer = new Customer();
		customer.setCustomer_id(customerRequestDto.getCustomer_id());
		customer.setFirstName(customerRequestDto.getFirstName());
		customer.setLastName(customerRequestDto.getLastName());
		customer.setTotalBalance(customerRequestDto.getTotalBalance());
		return customer;
	}

	private Bank convertBankDtotoBank(BankRequestDto bank) {
		Bank bank1 = new Bank();
		bank1.setBank_id(bank.getBank_id());
		bank1.setAbbreviation(bank.getAbbreviation());
		bank1.setFullName(bank.getFullName());
		return bank1;
	}

	@Override
	public TransactionResponseDto doTransaction(long senderAccountNumber, long receiverAccountNumber, double amount) {
		Optional<User> user = userRepository.findByEmail(getEmailFromSecurityContext());
		Customer customer=user.get().getCustomer();
		List<Account> accounts = user.get().getCustomer().getAccounts();
		 if(!customer.isActive()) {
				throw new NoRecordFoundException("Customer is not activated "+customer.getCustomer_id());
			}
		for (Account account : accounts) {
			if (account.getAccountNumber() == senderAccountNumber) {
				Account senderAccount = accountRepository.findById(senderAccountNumber).orElse(null);
				Account receiverAccount = accountRepository.findById(receiverAccountNumber).orElse(null);
				if(!senderAccount.isActive()) {
					throw new NoRecordFoundException("Account is not activated "+senderAccount.getAccountNumber());
					}
				if(!receiverAccount.isActive()) {
					throw new NoRecordFoundException("Account is not activated "+receiverAccount.getAccountNumber());
					}
				if (senderAccount == null || receiverAccount == null) {
					throw new NoRecordFoundException("Please check the sender account number " + senderAccountNumber
							+ " and receiver account number " + receiverAccountNumber);
				}
				if (senderAccount.equals(receiverAccount)) {
					throw new NoRecordFoundException("self transfer to the same account number not possible");
				}
				if (senderAccount.getBalance() < amount) {
					throw new NoRecordFoundException("Insufficient Funds please check the balance again");
				}
				senderAccount.setBalance(senderAccount.getBalance() - amount);
				receiverAccount.setBalance(receiverAccount.getBalance() + amount);
				accountRepository.save(senderAccount);
				accountRepository.save(receiverAccount);
				Customer senderCustomer = senderAccount.getCustomer();
				Customer receiverCustomer = receiverAccount.getCustomer();
				senderCustomer.setTotalBalance(senderCustomer.getTotalBalance() - amount);
				receiverCustomer.setTotalBalance(receiverCustomer.getTotalBalance() + amount);

				customerRepository.save(senderCustomer);
				customerRepository.save(receiverCustomer);
				Transaction transaction = new Transaction();
				transaction.setAmount(amount);
				transaction.setSenderAccount(senderAccount);
				transaction.setReceiverAccount(receiverAccount);
				transaction.setTransactionType(TransactionType.Transfer);
				User senderUser=senderCustomer.getUser();
				User receiverUser=receiverCustomer.getUser();
				sendMailToTheUsers(senderUser,receiverUser,senderCustomer,senderAccountNumber,transaction,receiverCustomer,receiverAccountNumber);
				return convertTransactiontoTransactionDto(transactionRepository.save(transaction));
			}
		}
		throw new NoRecordFoundException("Your account number is wrong");

	}

	private TransactionResponseDto convertTransactiontoTransactionDto(Transaction save) {

		TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
		transactionResponseDto.setAmount(save.getAmount());
		transactionResponseDto.setId(save.getId());
		transactionResponseDto.setSenderAccount(convertAccountTransactionToAccountResponseDto(save.getSenderAccount()));
		transactionResponseDto
				.setReceiverAccount(convertAccountTransactionToAccountResponseDto(save.getReceiverAccount()));
		transactionResponseDto.setTransactionDate(save.getTransactionDate());
		transactionResponseDto.setTransactionType(save.getTransactionType());

		return transactionResponseDto;

	}

	private List<TransactionResponseDto> convertTransactiontoTransactionDto(List<Transaction> all) {

		List<TransactionResponseDto> transactionResponseDto = new ArrayList<>();
		for (Transaction t : all) {
			transactionResponseDto.add(convertTransactiontoTransactionDto(t));

		}
		return transactionResponseDto;
	}

//	@Override
//	public PagedResponse<TransactionResponseDto> getAllTransaction(LocalDateTime fromDate, LocalDateTime toDate,
//			int page, int size, String sortBy, String direction) {
//		Sort sort = Sort.by(sortBy);
//		if (direction.equalsIgnoreCase("desc")) {
//			sort = sort.descending();
//		} else {
//			sort = sort.ascending();
//		}
//
//		PageRequest pageRequest = PageRequest.of(page, size, sort);
//		System.out.println("Page request: " + pageRequest);
//		Page<Transaction> pagedResponse = transactionRepository.findAllByTransactionDateBetween(fromDate, toDate,
//				pageRequest);
//		System.out.println("Fetched transactions: " + convertTransactiontoTransactionDto(pagedResponse.getContent()));
//		PagedResponse<TransactionResponseDto> response = new PagedResponse<>(
//				convertTransactiontoTransactionDto(pagedResponse.getContent()), pagedResponse.getNumber(),
//				pagedResponse.getSize(), pagedResponse.getTotalElements(), pagedResponse.getTotalPages(),
//				pagedResponse.isLast());
//		return response;
//	}

	private List<TransactionResponseDto> covertPassbooktopassbookDto(List<Transaction> viewPassbook, long accountNo) {
		List<TransactionResponseDto> transactionResponseDtos = new ArrayList<>();
		for (Transaction t : viewPassbook) {
			transactionResponseDtos.add(covertPassbooktopassbookDto(t, accountNo));
		}
		return transactionResponseDtos;
	}

	private TransactionResponseDto covertPassbooktopassbookDto(Transaction t, long accountNo) {
		TransactionResponseDto transactionDto = new TransactionResponseDto();

		if ((t.getSenderAccount().getAccountNumber()) == accountNo) {
			transactionDto.setTransactionType(TransactionType.Debit);

		} else {
			transactionDto.setTransactionType(TransactionType.Credit);
		}

		transactionDto.setAmount(t.getAmount());
		transactionDto.setId(t.getId());
		transactionDto.setReceiverAccount(convertAccounttoAccountResponseDto(t.getReceiverAccount()));
		transactionDto.setSenderAccount(convertAccounttoAccountResponseDto(t.getSenderAccount()));
		transactionDto.setTransactionDate(t.getTransactionDate());
		return transactionDto;
	}

	@Override
	public UserResponseDto createCustomer(CustomerRequestDto customerRequestDto, long userID) {
		User user = userRepository.findById(userID).orElse(null);
		if (user == null) {
			throw new NoRecordFoundException("User not found with the following id " + userID);
		}
		if (user.getCustomer() != null) {
			throw new NoRecordFoundException("Customer already assigned cannot create another customer to the user");
		}
		Customer customer = convertCustomerRequestToCustomer(customerRequestDto);
		user.setCustomer(customer);
		String subject = "Welcome to SBI! Your Customer ID Has Been Successfully Created";
		String emailBody = "Dear [Customer Name],\n\n"
			    + "Welcome to SBI! We are excited to have you join our community.\n\n"
			    + "We are pleased to confirm that your Customer ID has been successfully created. Our team is now working on completing the setup of your account. You can expect your account to be fully operational within a few days.\n\n"
			    + "While you wait, we encourage you to familiarize yourself with the range of services we offer. We are committed to providing you with an exceptional banking experience.\n\n"
			    + "Should you have any questions or require assistance, please feel free to reach out to our customer service team. We are here to support you at every step.\n\n"
			    + "Thank you for choosing SBI. We look forward to assisting you in achieving your financial goals.\n\n"
			    + "Sincerely,\n"
			    + "[Your Name]\n"
			    + "Customer Relations Team\n"
			    + "SBI";
		sendEmail(user.getEmail(), subject, emailBody);
		return convertUserToUserDto(userRepository.save(user));
	}

	private void sendEmail(String toMail, String subject, String emailBody) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(fromMail);
		mailMessage.setTo(toMail);
		mailMessage.setText(emailBody);
		mailMessage.setSubject(subject);
		javaMailSender.send(mailMessage);
	}

	private UserResponseDto convertUserToUserDto(User save) {
		UserResponseDto responseDto = new UserResponseDto();
		responseDto.setId(save.getId());
		responseDto.setRoles(save.getRoles());
		responseDto.setCustomer(convertCustomerToCustomerResponseDto(save.getCustomer()));
		responseDto.setEmail(save.getEmail());
		responseDto.setPassword(save.getPassword());
		return responseDto;
	}

	private CustomerResponseDto convertCustomerToCustomerResponseDto(Customer save) {
		CustomerResponseDto customerResponseDto = new CustomerResponseDto();
		customerResponseDto.setCustomer_id(save.getCustomer_id());
		customerResponseDto.setFirstName(save.getFirstName());
		customerResponseDto.setLastName(save.getLastName());
		customerResponseDto.setTotalBalance(save.getTotalBalance());
		return customerResponseDto;
	}

	private Customer convertCustomerRequestToCustomer(CustomerRequestDto customerRequestDto) {
		Customer customer = new Customer();
		customer.setFirstName(customerRequestDto.getFirstName());
		customer.setLastName(customerRequestDto.getLastName());
		customer.setTotalBalance(customerRequestDto.getTotalBalance());
		return customer;
	}

	@Override
	public String updateProfile(ProfileRequestDto profileRequestDto) {
		User user = userRepository.findByEmail(getEmailFromSecurityContext()).orElse(null);
		if (user.getCustomer() == null) {
			throw new NoRecordFoundException("Cannot update the customer details still you havn't have customer id");
		}
		Customer customer = user.getCustomer();
		if (profileRequestDto.getEmail() != null && !profileRequestDto.getEmail().isEmpty()
				&& profileRequestDto.getEmail().length() != 0) {
			user.setEmail(profileRequestDto.getEmail());
		}
		if (profileRequestDto.getFirstName() != null && !profileRequestDto.getFirstName().isEmpty()
				&& profileRequestDto.getFirstName().length() != 0) {
			customer.setFirstName(profileRequestDto.getFirstName());
		}
		if (profileRequestDto.getLastName() != null && !profileRequestDto.getLastName().isEmpty()
				&& profileRequestDto.getLastName().length() != 0) {
			customer.setLastName(profileRequestDto.getLastName());
		}
		if (profileRequestDto.getPassword() != null && !profileRequestDto.getPassword().isEmpty()
				&& profileRequestDto.getPassword().length() != 0) {
			user.setPassword(passwordEncoder.encode(profileRequestDto.getPassword()));
		}

		userRepository.save(user);

		return "user succesfully updated";
	}

	private String getEmailFromSecurityContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			return userDetails.getUsername();
		}
		return null;
	}

	@Override
	public PagedResponse<TransactionResponseDto> viewPassbook(long accountNumber, LocalDateTime from, LocalDateTime to,
			int page, int size, String sortBy, String direction) throws DocumentException, IOException, MessagingException {
		Sort sort = Sort.by(sortBy);
		if (direction.equalsIgnoreCase(Sort.Direction.DESC.name())) {
			sort.descending();
		} else {
			sort.ascending();
		}

		String email = getEmailFromSecurityContext();
		Optional<User> user = userRepository.findByEmail(email);
		if (user.get().getCustomer() == null) {
			throw new NoRecordFoundException("still you havn't have customer id");
		}
		List<Account> accounts = user.get().getCustomer().getAccounts();
		for (Account acc : accounts) {
			if (acc.getAccountNumber() == accountNumber) {
				Account account = accountRepository.findById(accountNumber).orElse(null);
				PageRequest pageRequest = PageRequest.of(page, size, sort);
				Page<Transaction> pagedResponse = transactionRepository.viewPassbook(account, from, to, pageRequest);
				String passbookSubject = "Your Passbook from Spring Bank Application";
				String passbookBody = "Dear " + user.get().getCustomer().getFirstName() + " "
						+ user.get().getCustomer().getLastName() + ",\n\n"
						+ "Attached to this email is your passbook for your recent transactions.\n\n"
						+ "You can review the details of your transactions for the specified period. If you have any questions or need further assistance, please do not hesitate to reach out to us.\n\n"
						+ "Thank you for choosing SBI. We are committed to providing you with the best service possible.\n\n"
						+ "Best regards,\n" + "Customer Relations Team\n" + "SBI";
				MailStructure mailStructure = new MailStructure();
				mailStructure.setToEmail(user.get().getEmail());
				mailStructure.setEmailBody(passbookBody);
				mailStructure.setSubject(passbookSubject);
				List<TransactionResponseDto> responseDTO = convertTransactiontoTransactionDto(pagedResponse.getContent(),accountNumber);
				byte[] passbookPdf = pdfUtil
						.generatePassbookPdf(user,responseDTO);
				emailUtil.sendEmailWithAttachment(mailStructure, passbookPdf);

				return new PagedResponse<TransactionResponseDto>(
						convertTransactiontoTransactionDto(pagedResponse.getContent(), accountNumber),
						pagedResponse.getNumber(), pagedResponse.getSize(), pagedResponse.getTotalElements(),
						pagedResponse.getTotalPages(), pagedResponse.isLast());
			}
		}
		throw new NoRecordFoundException("Please give valid account number");

	}

	private List<TransactionResponseDto> convertTransactiontoTransactionDto(List<Transaction> passbook,
			long accountNumber) {
		List<TransactionResponseDto> list = new ArrayList<>();
		for (Transaction transaction : passbook) {
			TransactionResponseDto responseDto = new TransactionResponseDto();
			responseDto.setAmount(transaction.getAmount());
			if (transaction.getReceiverAccount() != null) {
				responseDto.setReceiverAccount(
						convertAccountTransactionToAccountResponseDto(transaction.getReceiverAccount()));
			}
			if (transaction.getSenderAccount() != null) {
				responseDto.setSenderAccount(
						convertAccountTransactionToAccountResponseDto(transaction.getSenderAccount()));
			}
			responseDto.setId(transaction.getId());
			responseDto.setTransactionDate(transaction.getTransactionDate());
			if (transaction.getSenderAccount() != null
					&& transaction.getSenderAccount().getAccountNumber() == accountNumber) {
				responseDto.setTransactionType(TransactionType.Debit);
			} else {
				responseDto.setTransactionType(TransactionType.Credit);
			}
			list.add(responseDto);
		}
		return list;
	}

	@Override
	public AccountResponseDto depositAmount(long accountNumber, double amount) {
		User user = userRepository.findByEmail(getEmailFromSecurityContext()).orElse(null);
		List<Account> accounts = user.getCustomer().getAccounts();
		Customer customer = user.getCustomer();
		if (customer == null) {
			throw new NoRecordFoundException("Customer not associated with the user");
		}
		if(!customer.isActive()) {
			throw new NoRecordFoundException("Customer is not activated and his id is "+customer.getCustomer_id());
		}
		
		for (Account account : accounts) {
			if (account.getAccountNumber() == accountNumber && isAccountActive(account)) {
				account.setBalance(account.getBalance() + amount);
				accountRepository.save(account);
				Double totalBalance = accountRepository.getTotalBalance(customer);
				customer.setTotalBalance(totalBalance);
				customerRepository.save(customer);
				Transaction transaction = new Transaction();
				transaction.setAmount(amount);
				transaction.setReceiverAccount(account);
				transaction.setTransactionType(TransactionType.Transfer);
				transactionRepository.save(transaction);
				String subject = "Notification: Your Account Has Been Credited at SBI!";
				String emailBody = "Dear " + customer.getFirstName() + " " + customer.getLastName() + ",\n\n"
				    + "We are pleased to notify you that your account has been credited with an amount of " + transaction.getAmount() + " on " + LocalDateTime.now() + ".\n\n"
				    + "Account Number: ######" +accountNumber+ "\n\n"
				    + "You can view the details of this transaction by logging into our application and checking your account transactions.\n\n"
				    + "If you have any questions or need further assistance, please contact our support team. We are here to ensure you have the best banking experience.\n\n"
				    + "Thank you for banking with Spring Unity Bank. We look forward to continuing to support your financial needs.\n\n"
				    + "Best regards,\n"
				    + "Customer Relations Team\n"
				    + "SBI";

				sendEmail(user.getEmail(), subject, emailBody);
				return convertAccounttoAccountResponseDto(account);
			}
		}
		throw new NoRecordFoundException("Please check account number properly");

	}

	@Override
	public List<AccountResponseDto> getAccounts() {
		User user = userRepository.findByEmail(getEmailFromSecurityContext()).orElse(null);
		return convertAccounttoAccountResponseDto(user.getCustomer().getAccounts());
	}

	private AccountResponseDto convertAccountTransactionToAccountResponseDto(Account account) {
		AccountResponseDto accountResponseDTO = new AccountResponseDto();
		if (account != null) {
			accountResponseDTO.setAccountNumber(account.getAccountNumber());
		}
		return accountResponseDTO;
	}

	@Override
	public String deleteCustomer(long customerID) {
		Customer customer = customerRepository.findById(customerID).orElse(null);
		if (customer == null) {
			throw new NoRecordFoundException("Customer not found with the id " + customerID);
		}
		if (!customer.isActive()) {
			throw new NoRecordFoundException("Customer is already deleted");
		}
		customer.setActive(false);
		List<Account> accounts = customer.getAccounts();
		for (Account account : accounts) {
			account.setActive(false);
		}
		customerRepository.save(customer);
		return "Customer deleted successfully";
	}

	@Override
	public String activateCustomer(long customerID) {
		Customer customer = customerRepository.findById(customerID).orElse(null);
		if (customer == null) {
			throw new NoRecordFoundException("Customer not found with the id " + customerID);
		}
		if (customer.isActive()) {
			throw new NoRecordFoundException("Customer is already active");
		}
		customer.setActive(true);
		customerRepository.save(customer);
		return "Customer activated successfully";
	}

	@Override
	public String deleteAccount(long accountNumber) {
		Account account = accountRepository.findById(accountNumber).orElse(null);
		Customer customer = account.getCustomer();
		User user2 = customer.getUser();
		String email = user2.getEmail();
		//Account account = accountRepository.findById(accountNumber).orElse(null);
		if (account == null) {
			throw new NoRecordFoundException("Account not found with the account number " + accountNumber);
		}
		if (!account.isActive()) {
			throw new NoRecordFoundException("Account is already deleted");
		}
		account.setActive(false);
		accountRepository.save(account);
		String subject="Account Deactivation";
		String emailBody="Your account has been deactivated. Account Number: "+accountNumber;
		sendEmail(email, subject, emailBody);
		return "Account deleted successfully";
	}

	@Override
	public String activateAccount(long accountNumber) {
	

		Account account = accountRepository.findById(accountNumber).orElse(null);
		Customer customer = account.getCustomer();
		User user2 = customer.getUser();
		String email = user2.getEmail();
		
		if (account == null) {
			throw new NoRecordFoundException("Account not found with the account number " + accountNumber);
		}
		if (account.isActive()) {
			throw new NoRecordFoundException("Account is already active");
		}
		if(!account.getCustomer().isActive()) {
			throw new NoRecordFoundException("Customer is not activated customerId: "+account.getCustomer().getCustomer_id());
		}
		account.setActive(true);
		accountRepository.save(account);
		String subject="Account Activation";
		String emailBody="Your account has been activated. Account Number: "+accountNumber;
		sendEmail(email, subject, emailBody);

		
		return "Account activated successfully";
	}

	@Override
	public AccountResponseDto viewBalance(long accountNumber) {
		String email = getEmailFromSecurityContext();
		
		Optional<User> user = userRepository.findByEmail(email);
		List<Account> accounts = user.get().getCustomer().getAccounts();
		for (Account account : accounts) {
			if (account.getAccountNumber() == accountNumber && isAccountActive(account)) {
				return convertAccounttoAccountResponseDto(account);
			}
		}
		throw new NoRecordFoundException("Please check the account number");
	}

	private boolean isAccountActive(Account account) {
		if (!account.isActive()) {
			throw new NoRecordFoundException("Your account is not activated");
		}
		return true;
	}
	
private void sendMailToTheUsers(User senderUser, User receiverUser, Customer senderCustomer, long senderAccountNumber, Transaction transaction, Customer receiverCustomer, long receiverAccountNumber) {
		
		String debitedsubject = "Notification: Your Account Has Been Debited at State Bank of India !";
		String debitedBody = "Dear " + senderCustomer.getFirstName() + " " + senderCustomer.getLastName() + ",\n\n"
		    + "We want to inform you that your account has been debited by an amount of " + transaction.getAmount() + " on " + transaction.getTransactionDate() + ".\n\n"
		    + "Account Number: ######" + senderAccountNumber + "\n\n"
		    + "If you did not authorize this transaction or have any concerns about it, please contact our support team immediately. We are here to assist you and ensure your account's security.\n\n"
		    + "You can view the details of this transaction by logging into our application and checking your account transactions.\n\n"
		    + "Thank you for banking with Unity Bank. We are dedicated to supporting your financial needs.\n\n"
		    + "Best regards,\n"
		    + "Customer Relations Team\n"
		    + "SBI";
		sendEmail(senderUser.getEmail(), debitedsubject, debitedBody);
		String creditedsubject = "Notification: Your Account Has Been Credited at SBI!";
		String creditedBody = "Dear " + receiverCustomer.getFirstName() + " " + receiverCustomer.getLastName() + ",\n\n"
		    + "We are pleased to notify you that your account has been credited with an amount of " + transaction.getAmount() + " on " + transaction.getTransactionDate() + ".\n\n"
		    + "Account Number: ######" + receiverAccountNumber + "\n\n"
		    + "You can view the details of this transaction by logging into our application and checking your account transactions.\n\n"
		    + "If you have any questions or need further assistance, please contact our support team. We are here to ensure you have the best banking experience.\n\n"
		    + "Thank you for banking with SBI. We look forward to continuing to support your financial needs.\n\n"
		    + "Best regards,\n"
		    + "Customer Relations Team\n"
		    + "SBI";
		sendEmail(receiverUser.getEmail(),creditedsubject,creditedBody);
		
	}

@Override
public PagedResponse<TransactionResponseDto> viewAllTransaction(LocalDateTime fromDate, LocalDateTime toDate,
		int page, int size, String sortBy, String direction) {
	Sort sort = Sort.by(sortBy);
	if (direction.equalsIgnoreCase("desc")) {
		sort = sort.descending();
	} else {
		sort = sort.ascending();
	}
	PageRequest pageRequest = PageRequest.of(page, size, sort);
	System.out.println("Page request: " + pageRequest);
	Page<Transaction> pagedResponse = transactionRepository.findAllByTransactionDateBetween(fromDate, toDate,
			pageRequest);

	PagedResponse<TransactionResponseDto> response = new PagedResponse<>(
			convertTransactiontoTransactionDto(pagedResponse.getContent()), pagedResponse.getNumber(),
			pagedResponse.getSize(), pagedResponse.getTotalElements(), pagedResponse.getTotalPages(),
			pagedResponse.isLast());
	return response;
}

}