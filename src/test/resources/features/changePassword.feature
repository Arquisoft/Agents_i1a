Feature: Being able to change the password
	Scenario: Change password of user 444
		 When I login with "444" , "juan123" and "2"
		 And I click in change password
		 And I introduce name "444" , old password "juan123" , kind "2" and new password "test123"
		 Then I see a correctly change message
		 
	Scenario: Change password of user 444
		 When I login with "444" , "test123" and "2"
		 And I click in change password
		 And I introduce name "444" , old password "test123" , kind "2" and new password "juan123"
		 Then I see a correctly change message