# User Story 2
@user_story
Feature: Searching Laptops by Criteria

  As a customer,
  I want to find laptops in the store catalog that match specific characteristics (including price),
  So that I can choose the best laptop for my needs.

  Scenario: Customer tries to find laptops by criteria
    Given Customer wants to find 'Apple' 'MacBook Pro' with 'M1' processor and 'SSD' drive
    When Customer tries to find models by defined criteria
    Then Customer should get suitable models
