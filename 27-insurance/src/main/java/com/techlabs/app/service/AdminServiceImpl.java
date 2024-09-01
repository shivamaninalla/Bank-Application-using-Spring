package com.techlabs.app.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techlabs.app.dto.AgentRequestDto;
import com.techlabs.app.dto.AgentResponseDto;
import com.techlabs.app.dto.EmployeeRequestDto;
import com.techlabs.app.dto.TaxSettingRequestDto;
import com.techlabs.app.dto.UserResponseDto;
import com.techlabs.app.entity.Agent;
import com.techlabs.app.entity.City;
import com.techlabs.app.entity.Employee;
import com.techlabs.app.entity.Role;
import com.techlabs.app.entity.TaxSetting;
import com.techlabs.app.entity.User;
import com.techlabs.app.exception.BankApiException;
import com.techlabs.app.repository.AgentRepository;
import com.techlabs.app.repository.CityRepository;
import com.techlabs.app.repository.EmployeeRepository;
import com.techlabs.app.repository.RoleRepository;
import com.techlabs.app.repository.TaxSettingRepository;
import com.techlabs.app.repository.UserRepository;
import com.techlabs.app.util.PagedResponse;

@Service
public class AdminServiceImpl implements AdminService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private EmployeeRepository employeeRepository;
    private AgentRepository agentRepository;
    private CityRepository cityRepository;
    private TaxSettingRepository taxSettingRepository;


    


	public AdminServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder, EmployeeRepository employeeRepository, AgentRepository agentRepository,
			CityRepository cityRepository, TaxSettingRepository taxSettingRepository) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.employeeRepository = employeeRepository;
		this.agentRepository = agentRepository;
		this.cityRepository = cityRepository;
		this.taxSettingRepository = taxSettingRepository;
	}

	@Transactional
    @Override
    public String registerEmployee(EmployeeRequestDto employeeRequestDto) {

    	
    	if (userRepository.existsByUsername(employeeRequestDto.getUsername())) {
            throw new BankApiException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }
        if (userRepository.existsByEmail(employeeRequestDto.getEmail())) {
            throw new BankApiException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }
        
        
//        convertEmployeeRequestDtoToEmployee(employeeRequestDto);

        User user = new User();
        user.setUsername(employeeRequestDto.getUsername());
        user.setEmail(employeeRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(employeeRequestDto.getPassword()));

        // Assign ROLE_EMPLOYEE to the User
        Role employeeRole = roleRepository.findByName("ROLE_EMPLOYEE")
                .orElseThrow(() -> new BankApiException(HttpStatus.BAD_REQUEST, "Role not found: ROLE_EMPLOYEE"));
        Set<Role> roles = new HashSet<>();
        roles.add(employeeRole);
        user.setRoles(roles);

        // Save the User entity
        User savedUser = userRepository.save(user);

        // Create and save the Employee entity
        Employee employee = new Employee();
        employee.setUser(savedUser);
        employee.setName(employeeRequestDto.getName());
        employee.setActive(employeeRequestDto.isActive());

        employeeRepository.save(employee);
		return "Employee registered successfully";
    }

//	private Employee convertEmployeeRequestDtoToEmployee(EmployeeRequestDto employeeRequestDto) {
//		Employee employee=new Employee();
//		employee.setActive(false);
//		employee.setName(employeeRequestDto.getName());
//		employee.setUser();
//		
//	
//		
//	}



	

    @Transactional
    @Override
    public String registerAgent(AgentRequestDto agentRequestDto) {
        // Check if username or email already exists
        if (userRepository.existsByUsername(agentRequestDto.getUsername())) {
            throw new BankApiException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }
        if (userRepository.existsByEmail(agentRequestDto.getEmail())) {
            throw new BankApiException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }

        // Create a new User entity and set basic details
        User user = new User();
        user.setUsername(agentRequestDto.getUsername());
        user.setEmail(agentRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(agentRequestDto.getPassword()));

        // Assign ROLE_AGENT to the User
        Role agentRole = roleRepository.findByName("ROLE_AGENT")
                .orElseThrow(() -> new BankApiException(HttpStatus.BAD_REQUEST, "Role not found: ROLE_AGENT"));
        Set<Role> roles = new HashSet<>();
        roles.add(agentRole);
        user.setRoles(roles);

        // Save the User entity
        User savedUser = userRepository.save(user);

        // Fetch the City entity using city_id from the DTO
        City city = cityRepository.findById(agentRequestDto.getCity_id())
                .orElseThrow(() -> new BankApiException(HttpStatus.BAD_REQUEST, "City not found with id: " + agentRequestDto.getCity_id()));

        // Create and save the Agent entity
        Agent agent = new Agent();
        agent.setUser(savedUser);
        agent.setName(agentRequestDto.getName());
        agent.setPhoneNumber(agentRequestDto.getPhoneNumber());
        agent.setCity(city);  // Set the fetched City object
        agent.setActive(agentRequestDto.isActive());
        agentRepository.save(agent);
        return "Agent Registered successfully";
    }
    
    

  @Override
  public String createTaxSetting(TaxSettingRequestDto taxSettingRequestDto) {
    TaxSetting taxSetting=new TaxSetting();
    taxSetting.setTaxPercentage(taxSettingRequestDto.getTaxPercentage());
    taxSetting.setUpdatedAt(taxSettingRequestDto.getUpdatedAt());
    taxSettingRepository.save(taxSetting);
    return "Tax Setting updated";
  }

@Override
public PagedResponse<AgentResponseDto> getAllAgents(int page, int size, String sortBy, String direction) {
	Sort sort = Sort.by(sortBy);
	System.out.println("In get all customers");
	if (direction.equalsIgnoreCase(Sort.Direction.DESC.name())) {
		sort = sort.descending();
	} else {
		sort = sort.ascending();
	}
	Pageable pageable = PageRequest.of(page, size, sort);
	Page<Agent> customerPage = agentRepository.findAll(pageable);
	
	List<AgentResponseDto> customerDto = convertAgentToAgentResponseDto(customerPage.getContent());
	
	return new PagedResponse<>(customerDto, customerPage.getNumber(), customerPage.getSize(),
			customerPage.getTotalElements(), customerPage.getTotalPages(), customerPage.isLast());

	
}
//private List<AgentResponseDto> convertAgenttoAgentResponseDto(List<Agent> content) {
//
//	return null;
//}



//private List<AgentResponseDto> convertCustomertoCustomerDto(List<Agent> all) {
//	List<AgentResponseDto> customers = new ArrayList<>();
//
//	for (Agent i : all) {
//		
//		Agent agent=new Agent();
//		agent.setActive(i.isActive());
//		agent.setAgentId(i.getAgentId());
//		agent.setCity(i.getCity());
//		agent.setName(i.getName());
//		agent.setPhoneNumber(i.getPhoneNumber());
////		User user=new User();
//		agent.getUser().setEmail(i.getUser().getEmail());
//		agent.getUser().setUsername(i.getUser().setUsername(null));
//
//	}
//	return customers;
//}

private List<AgentResponseDto> convertAgentToAgentResponseDto(List<Agent> allAgents) {
    List<AgentResponseDto> agentDtos = new ArrayList<>();

    for (Agent agent : allAgents) {
        // Create a new AgentResponseDto object
        AgentResponseDto agentDto = new AgentResponseDto();
        
        // Set the simple fields
        agentDto.setAgentId(agent.getAgentId());
        agentDto.setName(agent.getName());
        agentDto.setPhoneNumber(agent.getPhoneNumber());
        agentDto.setCity(agent.getCity());
        agentDto.setActive(agent.isActive());

        // Convert the associated User entity to a UserResponseDto
        UserResponseDto userDto = new UserResponseDto();
        userDto.setId(agent.getUser().getId());
        userDto.setEmail(agent.getUser().getEmail());
        userDto.setUsername(agent.getUser().getUsername());
        // Password is generally not included in response DTOs for security reasons
        // userDto.setPassword(agent.getUser().getPassword());

        // Set the UserResponseDto in the AgentResponseDto
        agentDto.setUserResponseDto(userDto);

        // Convert and set the associated collections if needed
        // Assuming that customers and commissions are already initialized
        agentDto.setCustomers(agent.getCustomers().stream().collect(Collectors.toList()));
        agentDto.setCommissions(agent.getCommissions().stream().collect(Collectors.toList()));

        // Add the AgentResponseDto to the list
        agentDtos.add(agentDto);
    }

    return agentDtos;
}

  
  
//  @Override
//  public PagedResponse<AgentResponseDto> getAllAgents(int page, int size, String sortBy, String direction) {
//      Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
//              : Sort.by(sortBy).descending();
//      Pageable pageable = PageRequest.of(page, size, sort);
//
//      Page<Agent> agentsPage = agentRepository.findAll(pageable);
//
//      List<AgentResponseDto> agentDtos = agentsPage.getContent().stream()
//              .map(this::convertAgentToResponseDto)
//              .collect(Collectors.toList());
//
//      PagedResponse<AgentResponseDto> pagedResponse = new PagedResponse<>();
//      pagedResponse.setContent(agentDtos);
//      pagedResponse.setPage(agentsPage.getNumber());
//      pagedResponse.setSize(agentsPage.getSize());
//      pagedResponse.setTotalElements(agentsPage.getTotalElements());
//      pagedResponse.setTotalPages(agentsPage.getTotalPages());
//      pagedResponse.setLast(agentsPage.isLast());
//
//      return pagedResponse;
//  }
//
//  private AgentResponseDto convertAgentToResponseDto(Agent agent) {
//      AgentResponseDto responseDto = new AgentResponseDto();
//      responseDto.setAgentId(agent.getAgentId());
//      responseDto.setName(agent.getName());
//      responseDto.setPhoneNumber(agent.getPhoneNumber());
//      responseDto.setCity(agent.getCity());
////      responseDto.setCustomers(agent.getCustomers().stream().collect(Collectors.toList()));
//      responseDto.setActive(agent.isActive());
////      responseDto.setCommissions(agent.getCommissions().stream().collect(Collectors.toList()));
//
//      // Convert and set the UserResponseDto
//      UserResponseDto userResponseDto = new UserResponseDto();
//      User user = agent.getUser();
//      userResponseDto.setId(user.getId());
//      userResponseDto.setUsername(user.getUsername());
//      userResponseDto.setEmail(user.getEmail());
//      responseDto.setUserResponseDto(userResponseDto);
//
//      return responseDto;
//  }



}
