package rs.ac.uns.ftn.informatika.jpa.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import rs.ac.uns.ftn.informatika.jpa.domain.Reon;
import rs.ac.uns.ftn.informatika.jpa.domain.ReservedTables;
import rs.ac.uns.ftn.informatika.jpa.domain.Restaurant;
import rs.ac.uns.ftn.informatika.jpa.domain.Tablee;
import rs.ac.uns.ftn.informatika.jpa.domain.User;
import rs.ac.uns.ftn.informatika.jpa.domain.users.Employee;
import rs.ac.uns.ftn.informatika.jpa.domain.users.Provider;
import rs.ac.uns.ftn.informatika.jpa.domain.users.RestaurantManager;
import rs.ac.uns.ftn.informatika.jpa.service.EmployeeService;
import rs.ac.uns.ftn.informatika.jpa.service.ManagerService;
import rs.ac.uns.ftn.informatika.jpa.service.ProviderService;
import rs.ac.uns.ftn.informatika.jpa.service.ReonService;
import rs.ac.uns.ftn.informatika.jpa.service.ReservedTablesService;
import rs.ac.uns.ftn.informatika.jpa.service.RestaurantService;
import rs.ac.uns.ftn.informatika.jpa.service.TableService;


@Controller
@RequestMapping("/restaurantManagerController")
public class RestaurantManagerController {

	@Autowired
	private RestaurantService restaurantService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private ReonService reonService;
	@Autowired
	private ProviderService providerService;
	@Autowired
	private TableService tableService;
	@Autowired
	private ReservedTablesService reservedTablesService;
	
	@RequestMapping(
			value = "/uzmiRestoranMenadzera",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Restaurant> uzmiRestoranMenadzera()  throws Exception {
		
		ServletRequestAttributes attr = (ServletRequestAttributes) 
			    RequestContextHolder.currentRequestAttributes();
		HttpSession session= attr.getRequest().getSession(true);
		User u = (User) session.getAttribute("korisnik");
		Restaurant r= null;
		RestaurantManager rm=null;
		if(u.getRole().equals("restaurantManager")){
			rm= this.managerService.getManager(u.getEmail());
			r = restaurantService.getRestaurant(rm.getRestaurant().getId());
		}
		
		return new ResponseEntity<Restaurant>(r, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/updateRestaurant",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Restaurant> updateRestaurant(@RequestBody Restaurant rest)  throws Exception {
		ServletRequestAttributes attr = (ServletRequestAttributes) 
			    RequestContextHolder.currentRequestAttributes();
		HttpSession session= attr.getRequest().getSession(true);
		User u = (User) session.getAttribute("korisnik");
		Restaurant r= null;
		RestaurantManager rm = null;
		if(u.getRole().equals("restaurantManager")){
			rm= this.managerService.getManager(u.getEmail());
			r = restaurantService.getRestaurant(rm.getRestaurant().getId());
		}
		Long id = r.getId();
		rest.setId(id);
		this.restaurantService.updateRestaurant(rest);
		return new ResponseEntity<Restaurant>(rest, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/updateRestaurantManager",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestaurantManager> updateRestaurantManager(@RequestBody RestaurantManager rest)  throws Exception {
		ServletRequestAttributes attr = (ServletRequestAttributes) 
			    RequestContextHolder.currentRequestAttributes();
		HttpSession session= attr.getRequest().getSession(true);
		User u = (User) session.getAttribute("korisnik");
		RestaurantManager rm= this.managerService.getManager(u.getEmail());
		rest.setId(rm.getId());
		this.managerService.updateManager(rest);
		return new ResponseEntity<RestaurantManager>(rest, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/uzmiMenadzera",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestaurantManager> uzmiMenadzera()  throws Exception {
		
		ServletRequestAttributes attr = (ServletRequestAttributes) 
			    RequestContextHolder.currentRequestAttributes();
		HttpSession session= attr.getRequest().getSession(true);
		User u = (User) session.getAttribute("korisnik");
		RestaurantManager rm= this.managerService.getManager(u.getEmail());
		
		return new ResponseEntity<RestaurantManager>(rm, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/addEmployee",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> addEmpolyee(@RequestBody Employee empl)  throws Exception {
		ServletRequestAttributes attr = (ServletRequestAttributes)
				RequestContextHolder.currentRequestAttributes();
		HttpSession session= attr.getRequest().getSession(true);
		User u = (User) session.getAttribute("korisnik");
		Restaurant r= null;
		RestaurantManager rm = null;
		if(u.getRole().equals("restaurantManager")){
			rm= this.managerService.getManager(u.getEmail());
			r = restaurantService.getRestaurant(rm.getRestaurant().getId());
		}
		empl.setRestaurant(r);
		this.employeeService.addEmployee(empl);
		return new ResponseEntity<Employee>(empl, HttpStatus.OK);
	}
	
	
	@RequestMapping(
			value = "/addProvider",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Provider> addProvider(@RequestBody Provider prov)  throws Exception {
		ServletRequestAttributes attr = (ServletRequestAttributes)
				RequestContextHolder.currentRequestAttributes();
		HttpSession session= attr.getRequest().getSession(true);
		User u = (User) session.getAttribute("korisnik");
		Restaurant r= null;
		RestaurantManager rm = null;
		if(u.getRole().equals("restaurantManager")){
			rm= this.managerService.getManager(u.getEmail());
			r = restaurantService.getRestaurant(rm.getRestaurant().getId());
		}
		prov.setRestaurant(r);
		this.providerService.addProvider(prov);
		return new ResponseEntity<Provider>(prov, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/uzmiRadnike",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Employee>> uzmiRadnike()  throws Exception {
		
		ServletRequestAttributes attr = (ServletRequestAttributes) 
			    RequestContextHolder.currentRequestAttributes();
		HttpSession session= attr.getRequest().getSession(true);
		User u = (User) session.getAttribute("korisnik");
		Restaurant r= null;
		RestaurantManager rm=null;
		if(u.getRole().equals("restaurantManager")){
			rm= this.managerService.getManager(u.getEmail());
			r = restaurantService.getRestaurant(rm.getRestaurant().getId());
		}
		ArrayList<Employee> temp= this.employeeService.getEmployeesOfRestaurant(r);
		
		return new ResponseEntity<ArrayList<Employee>>(temp, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/uzmiKonobare",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Employee>> uzmiKonobare()  throws Exception {
		
		ServletRequestAttributes attr = (ServletRequestAttributes) 
			    RequestContextHolder.currentRequestAttributes();
		HttpSession session= attr.getRequest().getSession(true);
		User u = (User) session.getAttribute("korisnik");
		Restaurant r= null;
		RestaurantManager rm=null;
		if(u.getRole().equals("restaurantManager")){
			rm= this.managerService.getManager(u.getEmail());
			r = restaurantService.getRestaurant(rm.getRestaurant().getId());
		}
		ArrayList<Employee> temp= this.employeeService.getEmployeesOfRestaurant(r);
		ArrayList<Employee> temp2 = new ArrayList<Employee>();
		
		for(int i=0; i<temp.size(); i++){
			if(temp.get(i).getRole().equals("waiter"))
				temp2.add(temp.get(i));
		}
		
		return new ResponseEntity<ArrayList<Employee>>(temp2, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/uzmiReone",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Reon>> uzmiReone()  throws Exception {
		
		ServletRequestAttributes attr = (ServletRequestAttributes) 
			    RequestContextHolder.currentRequestAttributes();
		HttpSession session= attr.getRequest().getSession(true);
		User u = (User) session.getAttribute("korisnik");
		Restaurant r= null;
		RestaurantManager rm=null;
		if(u.getRole().equals("restaurantManager")){
			rm= this.managerService.getManager(u.getEmail());
			r = restaurantService.getRestaurant(rm.getRestaurant().getId());
		}
		ArrayList<Reon> t= this.reonService.getReonsOfRestorans(r);
		
		
		return new ResponseEntity<ArrayList<Reon>>(t, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/getRestaurant/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Restaurant> getRestaurant(@PathVariable Long id)  throws Exception {
		Restaurant r= this.restaurantService.getRestaurant(id);
		return new ResponseEntity<Restaurant>(r, HttpStatus.OK);
	}
	
	
	@RequestMapping(
			value = "/getTablesForRestaurant",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Tablee>> getTablesForRestaurant()  throws Exception {
		
		ServletRequestAttributes attr = (ServletRequestAttributes) 
			    RequestContextHolder.currentRequestAttributes();
		HttpSession session= attr.getRequest().getSession(true);
		User u = (User) session.getAttribute("korisnik");
		RestaurantManager rm= this.managerService.getManager(u.getEmail());
		Restaurant restt = this.restaurantService.getRestaurant(rm.getRestaurant().getId());
		ArrayList<Tablee> tables = tableService.findByRestaurant(restt);
		
		ArrayList<Tablee> temp = new ArrayList<Tablee>();
		for(int i=0; i<tables.size(); i++){
			if(tables.get(i).isExist()){
				temp.add(tables.get(i));
			}
		}
		
		return new ResponseEntity<ArrayList<Tablee>>(temp, HttpStatus.OK);
	}
	
	
	@RequestMapping(
			value = "/getReservedTables",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<ReservedTables>> getReservedTables()  throws Exception {
		

		ServletRequestAttributes attr = (ServletRequestAttributes) 
			    RequestContextHolder.currentRequestAttributes();
		HttpSession session= attr.getRequest().getSession(true);
		User u = (User) session.getAttribute("korisnik");
		RestaurantManager rm= this.managerService.getManager(u.getEmail());
		Restaurant restt = this.restaurantService.getRestaurant(rm.getRestaurant().getId());
		
		ArrayList<ReservedTables> timetables = this.reservedTablesService.findByRestaurant(restt);
		
		return new ResponseEntity<ArrayList<ReservedTables>>((ArrayList<ReservedTables>) timetables, HttpStatus.OK);
	
	}
}
