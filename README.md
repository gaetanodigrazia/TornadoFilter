# TornadoFilter

## Introduction


## Step to make it work
### First step

Adding the dependency to the pom

<dependency>
         <groupId>com.tornadomicroservice.filter</groupId>
         <artifactId>tornado-filter</artifactId>
         <scope>system</scope>
         <version>0.1.0</version>
         <systemPath>Your\Path\To\The\Jar\TornadoFilter.jar</systemPath>
</dependency>


### Second step
Create a package .filters in your project


### Third step
Create a new class called: <YourEntityName>Filter and paste the code below into


import org.springframework.stereotype.Component;

@Component
public class <YourEntityName>Filter extends Filter {
	
	public <YourEntityName>Filter() throws NoSuchMethodException, SecurityException {
		super.setInjectedClass(<YourEntityName>ServiceImpl.class);
		super.initCases();
	}
}


### Fourth step
Paste the code below in your Rest Controller

@GetMapping(value = "/search", produces = "application/json")
	public List<User> search<YourEntityName>(@RequestParam(name = "searchMethod") String searchMethod,
	@RequestParam(name = "value") Object... params) {	
	return (List<YourEntityName>) <YourEntityName>Filter.filter(searchMethod, params);
}

@GetMapping(value = "/chainSearch")
	public List<<YourEntityName>>  chainSearch(@RequestParam(name = "searchMethod") Object... searchMethods) {
	return (List<<YourEntityName>>) <YourEntityName>Filter.chainFilter(searchMethods);
}

### Final step
You can now digit:
	- localhost:8080/user/chainSearch?searchMethod=getById(202)&searchMethod=getByFirstname(Guido)
	- localhost:8080/user/search?searchMethod=getByFirstnameLastname&value=Guido&value=Rossi













