Feature: DevScratchpad

  #----------------------------------------------  
  #----------------------------------------------
  Scenario: PrintTableContents
    When I print the contents of the following table:
      | Name             | Value |
      | DaysInYear       |   365 |
      | Days in Week     |     7 |
      | Hours in Day     |    24 |
      | Minutes in Hours |    60 |

  #----------------------------------------------
  #----------------------------------------------
  Scenario: CompareValuesInTable  
    When the values in the following table are verified:
      | Name  | Expected | Actual |
      | Test1 | Pass     | Pass   |
      | Test2 | Pass     | Fail   |
      | Test3 | Fail     | Pass   |
      | Test4 | Fail     | Fail   |
      
      
      
Scenario: Blah
	When The user gets something called "something"      
            
