Feature: Being able to login
	Scenario: Login with username 444
	    Given a list of agents:
	      | name    | password | kind | 
	      | 4    	| maria123 | 1	  |
	      | 6       | julia123 | 1    |
	      | 444     | juan123  | 2	  |
	    When I login with name "444" , password "juan123" and kind "2"
	    Then I can see my data
	    
	Scenario: Login with username 4
		Given a list of agents:
	      | name    | password | kind | 
	      | 4    	| maria123 | 1	  |
	      | 6       | julia123 | 1    |
	      | 444     | juan123  | 2	  |
	    When I login with name "4" , password "maria123" and kind "1"
	    Then I can see my data
	  
	Scenario: Login with username 6
		Given a list of agents:
	      | name    | password | kind | 
	      | 4    	| maria123 | 1	  |
	      | 6       | julia123 | 1    |
	      | 444     | juan123  | 2	  |
	    When I login with name "6" , password "julia123" and kind "1"
	    Then I can see my data
	    
	Scenario: Login with wrong password of username 444
	    When I login with name "444" , password "juan2" and kind "2"
	    Then I see an error message
	    
	Scenario: Login with wrong password of username 4
		Given a list of agents:
	      | name    | password | kind | 
	      | 4    	| maria123 | 1	  |
	      | 6       | julia123 | 1    |
	      | 444     | juan123  | 2	  |
	    When I login with name "4" , password "maria12" and kind "1"
	    Then I see an error message
	    
	Scenario: Login with wrong password of username 6
	    When I login with name "6" , password "julia12" and kind "1"
	    Then I see an error message
	    
	Scenario: Login with wrong kind of username 444
		Given a list of agents:
	      | name    | password | kind | 
	      | 4    	| maria123 | 1	  |
	      | 6       | julia123 | 1    |
	      | 444     | juan123  | 2	  |
	    When I login with name "444" , password "juan123" and kind "1"
	    Then I see an error message
	    
	Scenario: Login with wrong kind of username 4
	    When I login with name "4" , password "maria123" and kind "2"
	    Then I see an error message
	    
	Scenario: Login with wrong kind of username 6
		Given a list of agents:
	      | name    | password | kind | 
	      | 4    	| maria123 | 1	  |
	      | 6       | julia123 | 1    |
	      | 444     | juan123  | 2	  |
	    When I login with name "6" , password "julia123" and kind "3"
	    Then I see an error message
	    
	Scenario: Login with wrong name of username 444
	    When I login with name "44" , password "juan123" and kind "2"
	    Then I see an error message
	    
	Scenario: Login with wrong name of username 4
		Given a list of agents:
	      | name    | password | kind | 
	      | 4    	| maria123 | 1	  |
	      | 6       | julia123 | 1    |
	      | 444     | juan123  | 2	  |
	    When I login with name "04" , password "maria123" and kind "1"
	    Then I see an error message
	    
	Scenario: Login with wrong name of username 6
	    When I login with name "67" , password "julia123" and kind "1"
	    Then I see an error message
