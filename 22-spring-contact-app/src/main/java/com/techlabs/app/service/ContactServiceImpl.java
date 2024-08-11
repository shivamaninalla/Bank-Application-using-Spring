package com.techlabs.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techlabs.app.dto.ContactDetailsRequestDto;
import com.techlabs.app.dto.ContactDetailsResponseDto;
import com.techlabs.app.dto.ContactRequestDto;
import com.techlabs.app.dto.ContactResponseDto;
import com.techlabs.app.dto.UserRequestDto;
import com.techlabs.app.dto.UserResponseDto;
import com.techlabs.app.entity.Contact;
import com.techlabs.app.entity.ContactDetails;
import com.techlabs.app.entity.ContactType;
import com.techlabs.app.entity.User;
import com.techlabs.app.exception.ContactApiException;
import com.techlabs.app.exception.NoUserRecordFoundException;
import com.techlabs.app.repository.ContactDetailsRepository;
import com.techlabs.app.repository.ContactRepository;
import com.techlabs.app.repository.UserRepository;
import com.techlabs.app.util.PagedResponse;

@Service
public class ContactServiceImpl implements ContactService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private ContactRepository contactRepository;
	private ContactDetailsRepository contactDetailsRepository;

	public ContactServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
			ContactRepository contactRepository, ContactDetailsRepository contactDetailsRepository) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.contactRepository = contactRepository;
		this.contactDetailsRepository = contactDetailsRepository;
	}

	@Override
	public List<UserResponseDto> getAllUsers() {

		List<User> users = userRepository.findAll();
		List<UserResponseDto> list = convertUserToUserResponseDto(users);
		return list;
	}

	private List<UserResponseDto> convertUserToUserResponseDto(List<User> users) {
		List<UserResponseDto> user = new ArrayList<>();
		for (User u : users) {
			UserResponseDto userResponseDto = new UserResponseDto();
			userResponseDto.setEmail(u.getEmail());
			userResponseDto.setFirstName(u.getFirstName());
			userResponseDto.setLastName(u.getLastName());
			userResponseDto.setUserId(u.getUserId());
			userResponseDto.setAdmin(u.isAdmin());
			userResponseDto.setActive(u.isActive());
			user.add(userResponseDto);
		}
		return user;

	}

	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		if (userRepository.existsByEmail(userRequestDto.getEmail())) {
			throw new ContactApiException(HttpStatus.BAD_REQUEST, "Email already exists!");
		}
		User user = convertUserRequestDtoToUser(userRequestDto);
		userRepository.save(user);
		return convertUserToUserResponseDto(user);
	}

	private UserResponseDto convertUserToUserResponseDto(User u) {
		UserResponseDto userResponseDto = new UserResponseDto();
		userResponseDto.setEmail(u.getEmail());
		userResponseDto.setFirstName(u.getFirstName());
		userResponseDto.setLastName(u.getLastName());
		userResponseDto.setUserId(u.getUserId());
		userResponseDto.setAdmin(u.isAdmin());
		userResponseDto.setActive(u.isActive());

		return userResponseDto;
	}

	private User convertUserRequestDtoToUser(UserRequestDto userRequestDto) {
		User user = new User();
		user.setUserId(userRequestDto.getUserId());
		user.setActive(userRequestDto.isActive());
		user.setEmail(userRequestDto.getEmail());
		user.setAdmin(userRequestDto.isAdmin());
		user.setFirstName(userRequestDto.getFirstName());
		user.setLastName(userRequestDto.getLastName());
		user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

		return user;

	}

	@Override
	public UserResponseDto updateUserProfile(UserRequestDto userRequestDto, int user_id) {

		User user = userRepository.findById(user_id).orElse(null);

		if (user != null) {
			userRequestDto.setUserId(user_id);
			if (userRequestDto.getFirstName() != null && !userRequestDto.getFirstName().isEmpty()) {
				user.setFirstName(userRequestDto.getFirstName());
			}
			if (userRequestDto.getLastName() != null && !userRequestDto.getLastName().isEmpty()) {
				user.setLastName(userRequestDto.getLastName());
			}
			if (userRequestDto.getEmail() != null && !userRequestDto.getEmail().isEmpty()) {
				user.setEmail((userRequestDto.getEmail()));
			}
			if (userRequestDto.getPassword() != null && !userRequestDto.getPassword().isEmpty()) {
				user.setPassword(((userRequestDto.getPassword())));
			}

			userRepository.save(user);
			return convertUserToUserResponseDto(user);
		} else {
			throw new NoUserRecordFoundException("No user found with this user_id: " + user_id);
		}
	}

	@Override
	public String deleteUser(int user_id) {
		User user = userRepository.findById(user_id).orElse(null);
		if (user == null) {
			throw new NoUserRecordFoundException("No user found with this user_id: " + user_id);

		}
		if (!user.isActive()) {
			throw new NoUserRecordFoundException("User is already deactivated: " + user_id);

		}
		user.setActive(false);
		userRepository.save(user);
		return "User deleted successfully";

	}

	@Override
	public UserResponseDto getUserById(int user_id) {
		User user = userRepository.findById(user_id).orElse(null);
		if (user == null) {
			throw new NoUserRecordFoundException("No user found with this user_id: " + user_id);

		}
		UserResponseDto dto = convertUserToUserResponseDto(user);
		return dto;
	}

	@Override
	public ContactResponseDto createContactforStaff(ContactRequestDto contactRequestDto) {
		Optional<User> user = userRepository.findByEmail(getEmailFromSecurityContext());

		// contactRequestDto.setUser(user.get());
		Contact contact = convertContactRequestDtoToContact(contactRequestDto, user.get());

		Contact save = contactRepository.save(contact);

		return convertContactToContactResponseDto(save);
	}

	private String getEmailFromSecurityContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			return userDetails.getUsername();
		}
		return null;
	}

	private ContactResponseDto convertContactToContactResponseDto(Contact save) {
		ContactResponseDto contactResponseDto = new ContactResponseDto();
		contactResponseDto.setFirstName(save.getFirstName());
		contactResponseDto.setLastName(save.getLastName());
		contactResponseDto.setId(save.getId());
		contactResponseDto.setActive(save.isActive());
		contactResponseDto.setContactDetails(convertContactDetailsToContactDetailsResponseDto(save.getContactDetails()) );
		// contactResponseDto.setUser(convertUserToUserResponseDto(save.getUser()));
		return contactResponseDto;
	}

	private Contact convertContactRequestDtoToContact(ContactRequestDto contactRequestDto, User user) {
		Contact contact = new Contact();
		contact.setFirstName(contactRequestDto.getFirstName());
		contact.setLastName(contactRequestDto.getLastName());
		contact.setId(contactRequestDto.getId());
		contact.setActive(true);
		contact.setUser(user);
		return contact;
	}

	@Override
	public PagedResponse<ContactResponseDto> getAllContactsofStaff(int page, int size, String sortBy,
			String direction) {
		Sort sort = Sort.by(sortBy);
		if (direction.equalsIgnoreCase("desc")) {
			sort = sort.descending();
		} else {
			sort = sort.ascending();
		}
		User user = userRepository.findByEmail(getEmailFromSecurityContext()).orElse(null);

		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Contact> contact = contactRepository.findByUser(user, pageable);

		List<ContactResponseDto> list = convertContactToContactResponseDto(contact.getContent());
		return new PagedResponse<>(list, contact.getNumber(), contact.getSize(), contact.getTotalElements(),
				contact.getTotalPages(), contact.isLast());
	}

	private List<ContactResponseDto> convertContactToContactResponseDto(List<Contact> all) {
		List<ContactResponseDto> contactResponseDto = new ArrayList<>();
		for (Contact c : all) {
			ContactResponseDto dto = new ContactResponseDto();
			dto.setActive(c.isActive());
			dto.setFirstName(c.getFirstName());
			dto.setId(c.getId());
			// dto.setUser(convertUserToUserResponseDto(c.getUser()));
			dto.setLastName(c.getLastName());
			dto.setContactDetails(convertContactDetailsToContactDetailsResponseDto(c.getContactDetails()));
			contactResponseDto.add(dto);
		}
		return contactResponseDto;
	}

	@Override
	public ContactResponseDto getContactByIdofStaff(int contact_id) {
		User user = userRepository.findByEmail(getEmailFromSecurityContext()).orElse(null);
		List<Contact> contacts = user.getContacts();
		for (Contact c : contacts) {
			if (c.getId() == contact_id) {

				ContactResponseDto dto = convertContactToContactResponseDto(c);
				return dto;
			}
		}
		throw new NoUserRecordFoundException("No contact with id: " + contact_id + " found in your contacts");

	}

	@Override
	public ContactResponseDto updateContactForStaff(ContactRequestDto contactRequestDto, int contact_id) {

		User user = userRepository.findByEmail(getEmailFromSecurityContext()).orElse(null);
		if (!user.isActive()) {
			throw new NoUserRecordFoundException("User is not in active state ");

		} else {
			List<Contact> contacts = user.getContacts();
			for (Contact c : contacts) {
				if (c.getId() == contact_id) {
					contactRequestDto.setId(contact_id);
					if (contactRequestDto.getFirstName() != null && !contactRequestDto.getFirstName().isEmpty()) {
						c.setFirstName(contactRequestDto.getFirstName());
					}
					if (contactRequestDto.getLastName() != null && !contactRequestDto.getLastName().isEmpty()) {
						c.setLastName(contactRequestDto.getLastName());
					}
					contactRepository.save(c);
					return convertContactToContactResponseDto(c);

				}
			}
		}
		throw new NoUserRecordFoundException("No contact with id: " + contact_id + " found in your contacts");

	}

	@Override
	public String deleteContactForStaff(int contact_id) {
		User user = userRepository.findByEmail(getEmailFromSecurityContext()).orElse(null);
		if (!user.isActive()) {
			throw new NoUserRecordFoundException("User is not in active state ");
		}

		List<Contact> contacts = user.getContacts();
		for (Contact c : contacts) {
			if (c.getId() == contact_id && c.isActive()) {

				c.setActive(false);
				contactRepository.save(c);
				userRepository.save(user);
				return "Contact is deleted successfully";
			} else {
				throw new NoUserRecordFoundException("Contact is already deleted. Contact id: " + contact_id);

			}

		}
		throw new NoUserRecordFoundException("No contact with id: " + contact_id + " found in your contacts");

	}

	@Override
	public ContactDetailsResponseDto createContactDetailsforContact(ContactDetailsRequestDto contactDetailsRequestDto,
			int contact_id) {
		User user = userRepository.findByEmail(getEmailFromSecurityContext()).orElse(null);
		if (!user.isActive()) {
			throw new NoUserRecordFoundException("User is not in active state ");
		}
		List<Contact> contacts = user.getContacts();
		for (Contact c : contacts) {
			if (c.getId() == contact_id) {

				ContactDetails contactDetails = convertContactDetailsRequestDtoToContactDetails(
						contactDetailsRequestDto);
				contactDetails.setContact(c);
				c.add(contactDetails);
				contactRepository.save(c);
				return convertContactDetailsToContactResponseDto(contactDetails);
			}
		}
		throw new NoUserRecordFoundException("No contact with id: " + contact_id + " found in your contacts");
	}

	private ContactDetailsResponseDto convertContactDetailsToContactResponseDto(ContactDetails contactDetails) {
		ContactDetailsResponseDto contactDetailsResponseDto = new ContactDetailsResponseDto();
		contactDetailsResponseDto.setContactType(contactDetails.getContactType());
		// contactDetailsResponseDto.setId(contactDetails.getId());
		return contactDetailsResponseDto;

	}

	private ContactDetails convertContactDetailsRequestDtoToContactDetails(
			ContactDetailsRequestDto contactDetailsRequestDto) {
		ContactDetails contactDetails = new ContactDetails();

		contactDetails.setContactType(contactDetailsRequestDto.getContactType());

		return contactDetails;

	}

	@Override
	public PagedResponse<ContactDetailsResponseDto> getAllContactDetailsofContact(int contact_id, int page, int size,
			String sortBy, String direction) {
		User user = userRepository.findByEmail(getEmailFromSecurityContext()).orElse(null);
		if (!user.isActive()) {
			throw new NoUserRecordFoundException("User is not in active state ");
		}
		Sort sort = Sort.by(sortBy);
		if (direction.equalsIgnoreCase("desc")) {
			sort = sort.descending();
		} else {
			sort = sort.ascending();
		}

		Contact contact = contactRepository.findById(contact_id).orElse(null);
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		Page<ContactDetails> contactDetails = contactDetailsRepository.findByContact(contact, pageRequest);

		List<ContactDetailsResponseDto> list = convertContactDetailsToContactDetailsResponseDto(
				contactDetails.getContent());
		return new PagedResponse<>(list, contactDetails.getNumber(), contactDetails.getSize(),
				contactDetails.getTotalElements(), contactDetails.getTotalPages(), contactDetails.isLast());
	}

//	private List<ContactDetailsResponseDto> getAllContactsDetails(Contact c) {
//		
//		List<ContactDetails> contacts = c.getContactDetails();
//		List<ContactDetailsResponseDto> list = convertContactDetailsToContactDetailsResponseDto(contacts);
//		return list;
//	}
//
	private List<ContactDetailsResponseDto> convertContactDetailsToContactDetailsResponseDto(
			List<ContactDetails> contacts) {
		List<ContactDetailsResponseDto> contactDetailsResonseDto = new ArrayList<>();
		for (ContactDetails c : contacts) {
			ContactDetailsResponseDto dto = new ContactDetailsResponseDto();
			dto.setContactType(c.getContactType());
			dto.setId(c.getId());
			contactDetailsResonseDto.add(dto);

		}
		return contactDetailsResonseDto;
	}

	@Override
	public ContactDetailsResponseDto updateContactDetailsofContact(ContactDetailsRequestDto contactDetailsRequestDto,
			int contact_id, int contact_details_id) {
		User user = userRepository.findByEmail(getEmailFromSecurityContext()).orElse(null);
	    if (user == null || !user.isActive()) {
	        throw new NoUserRecordFoundException("User is not in active state ");
	    }

	    List<Contact> contacts = user.getContacts();
	    for (Contact contact : contacts) {
	        if (contact.getId() == contact_id) {
	            List<ContactDetails> contactDetails = contact.getContactDetails();
	            boolean found = false;
	            for (ContactDetails contactdetail : contactDetails) {
	                if (contactdetail.getId() == contact_details_id) {
	                    found = true;
	                    if (contactDetailsRequestDto.getContactType() != null) {
	                        contactdetail.setContactType(contactDetailsRequestDto.getContactType());
	                        contactRepository.save(contact);
	                        return convertContactDetailsToContactResponseDto(contactdetail);
	                    }
	                }
	            }
	            if (!found) {
	                throw new NoUserRecordFoundException("Contact details not found for id: " + contact_details_id);
	            }
	        }
	    }

	    return null;
	}

	@Override
	public ContactDetailsResponseDto getContactDetailsById(int contact_id, int contact_details_id) {
		User user = userRepository.findByEmail(getEmailFromSecurityContext()).orElse(null);
		if (!user.isActive()) {
			throw new NoUserRecordFoundException("User is not in active state ");
		}
		List<Contact> contacts = user.getContacts();
		boolean x = false;
		for (Contact c : contacts) {
			if (c.getId() == contact_id) {
				List<ContactDetails> contactDetails = c.getContactDetails();
				for (ContactDetails cd : contactDetails) {
					if (cd.getId() == contact_details_id) {
						x = true;
						ContactDetailsResponseDto contactDetailsResponseDto = new ContactDetailsResponseDto();
						contactDetailsResponseDto.setId(cd.getId());
						contactDetailsResponseDto.setContactType(cd.getContactType());
						return contactDetailsResponseDto;
					}

				}

			}
			if (!x) {
				throw new NoUserRecordFoundException(
						"No Contact Details found with id: " + contact_details_id + " found in your contacts details");

			}
		}
		throw new NoUserRecordFoundException("No contact with id: " + contact_id + " found in your contacts");
	}

//	@Override
//	public String deleteContactDetailsById(int contact_id, int contact_details_id) {
//		User user = userRepository.findByEmail(getEmailFromSecurityContext()).orElse(null);
//		if (!user.isActive()) {
//			throw new NoUserRecordFoundException("User is not in active state ");
//		}
//		List<Contact> contacts = user.getContacts();
//		boolean x = false;
//		for (Contact c : contacts) {
//			if (c.getId() == contact_id) {
//				List<ContactDetails> contactDetails = c.getContactDetails();
//				for (ContactDetails cd : contactDetails) {
//					
//					if (cd.getId() == contact_details_id) {
//						x=true;
//						c.remove(cd);
//						contactRepository.save(c);
//						return "Contact Details deleted suuccessfully";
//					}
//				}
//			}
//			if (!x) {
//				throw new NoUserRecordFoundException(
//						"No Contact Details found with id: " + contact_details_id + " found in your contacts details");
//			}
//
//		}
//		return "No contact with id found";
//	}
	
	@Override
	public String deleteContactDetailsById(int contactId, int contactDetailsId) {
	User user = userRepository.findByEmail(getEmailFromSecurityContext()).orElse(null);
	    
	    if (user == null || !user.isActive()) {
	        throw new NoUserRecordFoundException("User is not in active state");
	    }
	    
	    List<Contact> contacts = user.getContacts();
	    for (Contact contact : contacts) {
	        if (contact.getId() == contactId) {
	            ContactDetails contactDetail = contactDetailsRepository.findById(contactDetailsId)
	                    .orElseThrow(() -> new NoUserRecordFoundException("Contact detail not found with id: " + contactDetailsId));

	            contact.getContactDetails().remove(contactDetail);
	            contactDetailsRepository.delete(contactDetail);  
	            
	            contactRepository.save(contact);
	            
	            return "Contact details deleted successfully";
	        }
	    }
	    
	    throw new NoUserRecordFoundException("Contact not found with id: " + contactId);
	
	}

}