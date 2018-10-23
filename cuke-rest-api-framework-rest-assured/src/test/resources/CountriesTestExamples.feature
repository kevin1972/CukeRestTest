##############################################################################################
# Feature: RestCountriesTests
#
##############################################################################################
Feature: RestCountriesTests
  In order to find information about countries
  As a curious person
  I want to find country information

  Background: 
    Given the following setup information:
      | Name    | Value                            |
      | baseUri | https://restcountries.eu/rest/v2 |


  #---------------------------------------------------------------------------
  Scenario: GetAllCountries
  #---------------------------------------------------------------------------
    Given the following setup information:
      | Name     | Value |
      | method   | GET   |
      | resource | /all  |
    
    When I make the call to the resource
    
    Then the response code should be "200"
    And "250" countries should be returned
    And the values should match the following table:
      | Field | JPath     | ExpectedValue |
      | name  | $[0].name | Afghanistan   |


  #---------------------------------------------------------------------------
  Scenario Outline: GetCountryByNameAndFullText
  #---------------------------------------------------------------------------
    Given the following setup information:
      | Name     | Value               |
      | method   | GET                 |
      | resource | /name/<CountryName> |
    
    And the following parameter information:
      | Parameter | Value |
      | fullText  | true  |
    
    When I make the call to the resource
    
    Then the response code should be "200"
    And the values should match the following table:
      | Item | Path      | Expected        |
      | name | $[0].name | <ExpectedValue> |

    Examples: 
      | CountryName | Field | JPath     | ExpectedValue            |
      | USA         | name  | $[0].name | United States of America |
      | Canada      | name  | $[0].name | Canada                   |
      | Mexico      | name  | $[0].name | Mexico                   |
