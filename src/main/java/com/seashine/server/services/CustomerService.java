package com.seashine.server.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.seashine.server.domain.Customer;
import com.seashine.server.repositories.CustomerRepository;
import com.seashine.server.services.exception.DataIntegrityException;
import com.seashine.server.services.exception.ObjectNotFoundException;
import com.seashine.server.specs.CustomSpecification;
import com.seashine.server.specs.SearchCriteria;
import com.seashine.server.specs.SearchOperation;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public Customer findById(Integer id) {
		Optional<Customer> obj = customerRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Class: " + Customer.class.getName()));
	}

	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	public Page<Customer> findPage(Integer page, Integer linesPerPage, String orderBy, String orderByDirection,
			String name) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(orderByDirection), orderBy);

		return customerRepository.findAll(getFilters(name), pageRequest);
	}

	@Transactional
	public Customer insert(Customer customer) {
		customer.setId(null);
		customer = customerRepository.save(customer);

		return customer;
	}

	public Customer update(Customer customer) {
		Customer customerDB = findById(customer.getId());
		updateData(customerDB, customer);
		return customerRepository.save(customerDB);
	}

	public void delete(Integer id) {
		try {
			customerRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Customer has orders!");
		}
	}

	private void updateData(Customer customerDB, Customer customer) {
		customerDB.setName(customer.getName());
	}

	private CustomSpecification<Customer> getFilters(String name) {
		CustomSpecification<Customer> specs = new CustomSpecification<Customer>();

		if (!name.equals("")) {
			specs.add(new SearchCriteria("name", name, SearchOperation.MATCH));
		}

		return specs;
	}

}
