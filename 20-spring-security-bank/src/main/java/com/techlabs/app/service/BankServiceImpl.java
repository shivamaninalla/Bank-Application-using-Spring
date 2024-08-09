package com.techlabs.app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.techlabs.app.dto.AccountRequestDTO;
import com.techlabs.app.dto.AccountResponseDTO;
import com.techlabs.app.dto.BankRequestDTO;
import com.techlabs.app.dto.CustomerRequestDTO;
import com.techlabs.app.dto.CustomerResponseDTO;
import com.techlabs.app.dto.TransactionResponseDTO;
import com.techlabs.app.dto.UserResponseDTO;
import com.techlabs.app.entity.Account;
import com.techlabs.app.entity.Bank;
import com.techlabs.app.entity.Customer;
import com.techlabs.app.entity.Transaction;
import com.techlabs.app.entity.TransactionType;
import com.techlabs.app.entity.User;
import com.techlabs.app.exception.NoRecordFoundException;
import com.techlabs.app.repository.AccountRepository;
import com.techlabs.app.repository.AdminRepository;
import com.techlabs.app.repository.BankRepository;
import com.techlabs.app.repository.CustomerRepository;
import com.techlabs.app.repository.TransactionRepository;
import com.techlabs.app.repository.UserRepository;
import com.techlabs.app.util.PagedResponse;

@Service
public class BankServiceImpl implements BankService{
	CustomerRepository customerRepository;
	
	AccountRepository accountRepository;
	BankRepository bankRepository;
	TransactionRepository transactionRepository;
	UserRepository userRepository;
	AdminRepository adminRepository;

	


	public BankServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository,
			BankRepository bankRepository, TransactionRepository transactionRepository, UserRepository userRepository,
			AdminRepository adminRepository) {
		super();
		this.customerRepository = customerRepository;
		this.accountRepository = accountRepository;
		this.bankRepository = bankRepository;
		this.transactionRepository = transactionRepository;
		this.userRepository = userRepository;
		this.adminRepository = adminRepository;
	}



	
	
	
	@Override
	public UserResponseDTO createCustomer(CustomerRequestDTO customerRequestDto,long userID) {
		User user = userRepository.findById(userID).orElse(null);
		if(user==null) {
			throw new NoRecordFoundException("User not found with the following id "+userID);
		}
		if(user.getCustomer()!=null) {
			throw new NoRecordFoundException("Customer already assigned cannot create another customer to the user");
		}
		Customer customer = convertCustomerRequestToCustomer(customerRequestDto);
		user.setCustomer(customer);
		return convertUserToUserDto(userRepository.save(user));
	}

	private UserResponseDTO convertUserToUserDto(User save) {
		UserResponseDTO responseDto=new UserResponseDTO();
		responseDto.setId(save.getId());
		responseDto.setRoles(save.getRoles());
		responseDto.setCustomerResponseDto(convertCustomerToCustomerResponseDto(save.getCustomer()));
		responseDto.setEmail(save.getEmail());
		responseDto.setPassword(save.getPassword());
		return responseDto;
	}

	private CustomerResponseDTO convertCustomerToCustomerResponseDto(Customer save) {
		CustomerResponseDTO customerResponseDto = new CustomerResponseDTO();
		customerResponseDto.setCustomer_id(save.getCustomer_id());
		customerResponseDto.setFirstName(save.getFirstName());
		customerResponseDto.setLastName(save.getLastName());
		customerResponseDto.setTotalBalance(save.getTotalBalance());
		return customerResponseDto;
	}

	private Customer convertCustomerRequestToCustomer(CustomerRequestDTO customerRequestDto) {
		Customer customer = new Customer();
		customer.setFirstName(customerRequestDto.getFirstName());
		customer.setLastName(customerRequestDto.getLastName());
		customer.setTotalBalance(customerRequestDto.getTotalBalance());
//		customer.setAccounts(convertAccountRequestDtoToAccount(customerRequestDto.getAccounts()));
		return customer;
	}



	@Override
	public PagedResponse<CustomerResponseDTO> getAllCustomers(int page, int size, String sortBy, String direction) {
		Sort sort = direction.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).descending()
				: Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Customer> pages = customerRepository.findAll(pageable);

		List<CustomerResponseDTO> data = convertCustomertoCustomerDto(pages.getContent());
		return new PagedResponse<>(data, pages.getNumber(), pages.getSize(),
				  pages.getTotalElements(), pages.getTotalPages(), pages.isLast());
		
		
	}


	private List<CustomerResponseDTO> convertCustomertoCustomerDto(List<Customer> all) {
		List<CustomerResponseDTO> customers=new ArrayList<>();
		
		for(Customer i:all) {
			CustomerResponseDTO customer=new CustomerResponseDTO();
			customer.setCustomer_id(i.getCustomer_id());
			customer.setFirstName(i.getFirstName());
			customer.setLastName(i.getLastName());
			customer.setTotalBalance(i.getTotalBalance());		
			customer.setAccounts(convertAccounttoAccountResponseDto(i.getAccounts()));
			customers.add(customer);
		}
		return customers;
	}

  

	private List<AccountResponseDTO> convertAccounttoAccountResponseDto(List<Account> accounts) {
	
	
		List<AccountResponseDTO> accountResponseDto=new ArrayList<>();
		for(Account account:accounts) {
			
			accountResponseDto.add(	convertAccounttoAccountResponseDto(account));
			
		}
		return accountResponseDto;
	}


	private AccountResponseDTO convertAccounttoAccountResponseDto(Account account) {
		
		AccountResponseDTO accountResponseDto= new AccountResponseDTO();
		accountResponseDto.setAccountNumber(account.getAccountNumber());
		accountResponseDto.setBalance(account.getBalance());
		return accountResponseDto;
	}


	@Override
	public CustomerResponseDTO addCustomer(CustomerRequestDTO customerrequestdto) {
		
		Customer customer=convertcustomerRequestDtoToCustomer(customerrequestdto);
		return convertCustomerTocustomerResponseDto(customerRepository.save(customer));
		
	}


	private CustomerResponseDTO convertCustomerTocustomerResponseDto(Customer customer) {
		CustomerResponseDTO customer1 =  new CustomerResponseDTO();
		customer1.setCustomer_id(customer.getCustomer_id());
		customer1.setFirstName(customer.getFirstName());
		customer1.setLastName(customer.getLastName());
		customer1.setTotalBalance(customer.getTotalBalance());
		return customer1;
	}


	private Customer convertcustomerRequestDtoToCustomer(CustomerRequestDTO customerrequestdto) {
		Customer customer =  new Customer();
		customer.setCustomer_id(customerrequestdto.getCustomer_id());
		customer.setFirstName(customerrequestdto.getFirstName());
		customer.setLastName(customerrequestdto.getLastName());
		customer.setTotalBalance(customerrequestdto.getTotalBalance());
		return customer;
	}


	@Override
	public String deleteCustomerById(long id) {
		
		
		Customer customer=customerRepository.findById(id).orElse(null);
		if(customer!=null) {
			customerRepository.deleteById(id);
			
		}
		else {
			return "cannot find id";
		}
		return "customer deleted Successfully";
		
	}


	@Override
	public CustomerResponseDTO findCustomerByid(long id) {
		Customer customer = customerRepository.findById(id).orElse(null);
		 
		return convertCustomerTocustomerResponseDto(customer);
	}


	@Override
	public CustomerResponseDTO addAccount(long cid,int bid) {
		
		 Account account = new Account();
		 Bank bank = bankRepository.findById(bid).orElse(null);
		
		 
		 if(bank != null) {
			 Customer customer = customerRepository.findById(cid).orElse(null);
			 
			 if(customer != null){
				 
				//return convertAccounttoAccountResponseDto(accountRepository.save(account));
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
				 return convertCustomerTocustomerResponseDto(save);
				 
			 }
		 }
		return null;
		
		 
	
	
	}


	private Account convertAcountResponseDtoToAccount(AccountRequestDTO accountRequestDto) {
		Account account=new Account();
		account.setAccountNumber(accountRequestDto.getAccountNumber());
		account.setBalance(accountRequestDto.getBalance());
		account.setBank(convertBankDtotoBank(accountRequestDto.getBankrequestDto()));
		account.setCustomer(convertcustomerDtoToCustomer(accountRequestDto.getCustomerRequestDto()));
		return account;
		
	}


	


	private Customer convertcustomerDtoToCustomer(CustomerRequestDTO customerRequestDto) {
	
		Customer customer = new Customer();
		customer.setCustomer_id(customerRequestDto.getCustomer_id());
		customer.setFirstName(customerRequestDto.getFirstName());
		customer.setLastName(customerRequestDto.getLastName());
		customer.setTotalBalance(customerRequestDto.getTotalBalance());
		return customer;
	}


	private Bank convertBankDtotoBank(BankRequestDTO bank) {
		Bank bank1 = new Bank();
		bank1.setBank_id(bank.getBank_id());
		bank1.setAbbreviation(bank.getAbbreviation());
		bank1.setFullName(bank.getFullName());
		return bank1;
	}


	@Override
	public TransactionResponseDTO doTransaction(long senderAccountno, long receiverAccountno, double amount) {
		
		
		Account senderAccount=accountRepository.findById(senderAccountno).orElse(null);
		Account receiverAccount=accountRepository.findById(receiverAccountno).orElse(null);
		
		if(senderAccount==null){
			throw new NoRecordFoundException("sender account number no found");
		}
		if(receiverAccount==null) {
			throw new NoRecordFoundException("recevier account number no found");
		}
		if(senderAccount==receiverAccount) {
			throw new NoRecordFoundException("both Account numbers are same");
		}
		if(senderAccount.getBalance()<amount) {
			throw new NoRecordFoundException("Insufficient Balance");
		}
		
	
			senderAccount.setBalance(senderAccount.getBalance()-amount);
			receiverAccount.setBalance(receiverAccount.getBalance()+amount);
			Customer customer = new Customer();

			Customer sender=senderAccount.getCustomer();
			sender.setTotalBalance(sender.getTotalBalance()-amount);
			
			Customer receiver=receiverAccount.getCustomer();
			receiver.setTotalBalance(receiver.getTotalBalance()+amount);
			
			customerRepository.save(sender);
			customerRepository.save(receiver);
			
			
			
			accountRepository.save(senderAccount);
			accountRepository.save(receiverAccount);
			
		Transaction transaction=new Transaction();
		transaction.setAmount(amount);
		transaction.setSenderAccount(senderAccount);
		transaction.setReceiverAccount(receiverAccount);
		transaction.setTransactionType(TransactionType.Transfer);
	
	    Transaction save = transactionRepository.save(transaction);
	 
	    
		return  convertTransactiontoTransactionDto(save);
			
		
	}


	private TransactionResponseDTO convertTransactiontoTransactionDto(Transaction save) {
		
		TransactionResponseDTO transactionResponseDto=new TransactionResponseDTO();
		transactionResponseDto.setAmount(save.getAmount());
		transactionResponseDto.setId(save.getId());
		transactionResponseDto.setSenderAccount(convertAccounttoAccountResponseDto(save.getSenderAccount()));
		transactionResponseDto.setReceiverAccount(convertAccounttoAccountResponseDto(save.getReceiverAccount()));
		transactionResponseDto.setTransactionDate(save.getTransactionDate());
		transactionResponseDto.setTransactionType(save.getTransactionType());
		
		return transactionResponseDto;
		
	}


	


	private List<TransactionResponseDTO> convertTransactiontoTransactionDto(List<Transaction> all) {
		
		List<TransactionResponseDTO> transactionResponseDto=new ArrayList<>();
		for(Transaction t:all) {
			transactionResponseDto.add(convertTransactiontoTransactionDto(t));
			
		}
		return transactionResponseDto;
	}


	@Override
	public PagedResponse<TransactionResponseDTO> viewAllTransaction(int page, int size, String sortBy, String direction) {
		
	//	List<TransactionResponseDTO> findAll=convertTransactiontoTransactionDto(transactionRepository.findAll());
		Sort sort = direction.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).descending()
				: Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Transaction> pages = transactionRepository.findAll(pageable);

		List<TransactionResponseDTO> data = convertTransactiontoTransactionDto(pages.getContent());
		return new PagedResponse<>(data, pages.getNumber(), pages.getSize(),
				  pages.getTotalElements(), pages.getTotalPages(), pages.isLast());
	}


	@Override
	public PagedResponse<TransactionResponseDTO> viewPassbook(long accountNo,int page, int size, String sortBy, String direction) {
		
		Account account= accountRepository.findById(accountNo).orElse(null);
		Sort sort = direction.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).descending()
				: Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Transaction> pages = transactionRepository.findAll(pageable);
		List<TransactionResponseDTO> transactionResponseDto= covertPassbooktopassbookDto(transactionRepository.viewPassbook(account),accountNo);

		//List<TransactionResponseDTO> data = covertPassbooktopassbookDto(pages.getContent());
		return new PagedResponse<>(transactionResponseDto, pages.getNumber(), pages.getSize(),
				  pages.getTotalElements(), pages.getTotalPages(), pages.isLast());
		
	//	List<TransactionResponseDTO> transactionResponseDto= covertPassbooktopassbookDto(transactionRepository.viewPassbook(account),accountNo);
	//	return transactionResponseDto;
		
		
		
		
		
	}


	private List<TransactionResponseDTO> covertPassbooktopassbookDto(List<Transaction> viewPassbook,long accountNo) {
		List<TransactionResponseDTO> transactionResponseDtos=new ArrayList<>();
		for(Transaction t :viewPassbook) {
			transactionResponseDtos.add(covertPassbooktopassbookDto(t,accountNo));
		}
		return transactionResponseDtos;
	}


	private TransactionResponseDTO covertPassbooktopassbookDto(Transaction t,long accountNo) {
		 TransactionResponseDTO transactionDto=new TransactionResponseDTO();
		 
		 if((t.getSenderAccount().getAccountNumber())==accountNo) {
			 transactionDto.setTransactionType(TransactionType.Debit);
			 
		 }
		 else {
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
	public List<TransactionResponseDTO> searchByDate(LocalDateTime fromDate, LocalDateTime toDate) {
		
		return convertTransactiontoTransactionDto(transactionRepository.findByTransactionDateBetween(fromDate,toDate));
	}





	
}
