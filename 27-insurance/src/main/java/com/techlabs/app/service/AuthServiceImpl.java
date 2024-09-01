package com.techlabs.app.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techlabs.app.dto.LoginDto;
import com.techlabs.app.dto.RegisterDto;
import com.techlabs.app.entity.Administrator;
import com.techlabs.app.entity.Agent;
import com.techlabs.app.entity.Customer;
import com.techlabs.app.entity.Employee;
import com.techlabs.app.entity.Role;
import com.techlabs.app.entity.User;
import com.techlabs.app.exception.BankApiException;
import com.techlabs.app.repository.AdminRepository;
import com.techlabs.app.repository.AgentRepository; // Add the AgentRepository
import com.techlabs.app.repository.CustomerRepository; // Add the CustomerRepository
import com.techlabs.app.repository.EmployeeRepository; // Add the EmployeeRepository
import com.techlabs.app.repository.RoleRepository;
import com.techlabs.app.repository.UserRepository;
import com.techlabs.app.security.JwtTokenProvider;

import jakarta.transaction.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private EmployeeRepository employeeRepository; // Add EmployeeRepository

	@Autowired
	private AgentRepository agentRepository; // Add AgentRepository

	@Autowired
	private CustomerRepository customerRepository; // Add CustomerRepository

	

	public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider,
			AdminRepository adminRepository, EmployeeRepository employeeRepository, AgentRepository agentRepository,
			CustomerRepository customerRepository) {
		super();
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.adminRepository = adminRepository;
		this.employeeRepository = employeeRepository;
		this.agentRepository = agentRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public String login(LoginDto loginDto) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return jwtTokenProvider.generateToken(authentication);
	}

	@Transactional
	@Override
	public String register(RegisterDto registerDto) {
		if (userRepository.existsByUsername(registerDto.getUsername())) {
			throw new BankApiException(HttpStatus.BAD_REQUEST, "Username already exists!");
		}

		if (userRepository.existsByEmail(registerDto.getEmail())) {
			throw new BankApiException(HttpStatus.BAD_REQUEST, "Email already exists!");
		}

		// Create a new User entity and set basic details
		User user = new User();
		user.setUsername(registerDto.getUsername());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

		// Assign roles to the User
		Set<Role> roles = new HashSet<>();
		for (String roleName : registerDto.getRoles()) {
			Role role = roleRepository.findByName(roleName)
					.orElseThrow(() -> new BankApiException(HttpStatus.BAD_REQUEST, "Role not found: " + roleName));
			if (!roleName.equals("ROLE_ADMIN") && !roleName.equals("ROLE_CUSTOMER")) {
				throw new BankApiException(HttpStatus.BAD_REQUEST, "Only Admins and Customers can self-register");
			}
			roles.add(role);
		}
		user.setRoles(roles);

		// Save the User entity
		userRepository.save(user);

		// Handle registration for Admin and Customer roles
		for (Role role : roles) {
			if (role.getName().equals("ROLE_ADMIN")) {
				registerAdministrator(user, registerDto);
			}
//			 else if (role.getName().equals("ROLE_CUSTOMER")) {
//				registerCustomer(user, registerDto);
//			}
		}

		return "User registered successfully!";
	}

	private void registerAdministrator(User user, RegisterDto registerDto) {
		Administrator administrator = new Administrator();
		administrator.setUser(user);
		administrator.setName(registerDto.getName());

		administrator.setPhoneNumber(registerDto.getPhone_number());
	    System.out.println("Phone number being processed: " + registerDto.getPhone_number());

		
		adminRepository.save(administrator);
	}

//	private void registerCustomer(User user, RegisterDto registerDto) {
//		Customer customer = new Customer();
//		customer.setUser(user);
//		customer.setName(registerDto.getName());
//		customer.setPhoneNumber(registerDto.getPhone_number());
//		customer.setAddress(registerDto.getAddress());
//		customerRepository.save(customer);
//	}

	// Methods for admin-initiated registration of Employees and Agents
//	public void registerEmployee(RegisterDto registerDto) {
//		User user = new User();
//		user.setUsername(registerDto.getUsername());
//		user.setEmail(registerDto.getEmail());
//		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
//		Set<Role> roles = new HashSet<>();
//		Role role = roleRepository.findByName("ROLE_EMPLOYEE")
//				.orElseThrow(() -> new BankApiException(HttpStatus.BAD_REQUEST, "Role not found: ROLE_EMPLOYEE"));
//		roles.add(role);
//		user.setRoles(roles);
//		userRepository.save(user);
//
//		Employee employee = new Employee();
//		employee.setUser(user);
//		employee.setName(registerDto.getName());
//		employee.setEmail(registerDto.getEmail());
//		employee.setAddress(registerDto.getAddress());
//		employee.setPhoneNumber(registerDto.getPhone_number());
//		employeeRepository.save(employee);
//	}

//	public void registerAgent(RegisterDto registerDto) {
//		User user = new User();
//		user.setUsername(registerDto.getUsername());
//		user.setEmail(registerDto.getEmail());
//		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
//		Set<Role> roles = new HashSet<>();
//		Role role = roleRepository.findByName("ROLE_AGENT")
//				.orElseThrow(() -> new BankApiException(HttpStatus.BAD_REQUEST, "Role not found: ROLE_AGENT"));
//		roles.add(role);
//		user.setRoles(roles);
//		userRepository.save(user);
//
//		Agent agent = new Agent();
//		agent.setUser(user);
//		agent.setName(registerDto.getName());
//		agent.setEmail(registerDto.getEmail());
//		agent.setPhoneNumber(registerDto.getPhone_number());
//		agent.setAddress(registerDto.getAddress());
//		agentRepository.save(agent);
//	}
}
