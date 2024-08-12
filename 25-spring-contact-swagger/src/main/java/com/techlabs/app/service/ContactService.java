package com.techlabs.app.service;

import java.util.List;

import com.techlabs.app.dto.ContactDetailsRequestDto;
import com.techlabs.app.dto.ContactDetailsResponseDto;
import com.techlabs.app.dto.ContactRequestDto;
import com.techlabs.app.dto.ContactResponseDto;
import com.techlabs.app.dto.UserRequestDto;
import com.techlabs.app.dto.UserResponseDto;
import com.techlabs.app.util.PagedResponse;

public interface ContactService{

	List<UserResponseDto> getAllUsers();

	UserResponseDto createUser(UserRequestDto userRequestDto);

	UserResponseDto updateUserProfile(UserRequestDto userRequestDto, int user_id);

	String deleteUser(int user_id);

	UserResponseDto getUserById(int user_id);

	ContactResponseDto createContactforStaff(ContactRequestDto contactRequestDto);

	PagedResponse<ContactResponseDto> getAllContactsofStaff(int page, int size, String sortBy, String direction);

	ContactResponseDto getContactByIdofStaff(int contact_id);

	ContactResponseDto updateContactForStaff(ContactRequestDto contactRequestDto, int contact_id);

	String deleteContactForStaff(int contact_id);

	ContactDetailsResponseDto createContactDetailsforContact(ContactDetailsRequestDto contactDetailsRequestDto, int contactId);

	PagedResponse<ContactDetailsResponseDto> getAllContactDetailsofContact(int contact_id,int page, int size,  String sortBy,
			String direction);

	ContactDetailsResponseDto updateContactDetailsofContact(ContactDetailsRequestDto contactDetailsRequestDto, int contact_id, int contact_details_id);

	ContactDetailsResponseDto getContactDetailsById(int contact_id, int contact_details_id);

	String deleteContactDetailsById(int contact_details_id, int contact_details_id2);

}
