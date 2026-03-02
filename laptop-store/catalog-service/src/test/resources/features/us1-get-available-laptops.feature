# User Story 1
@user_story
Feature: Getting Available Laptops from Catalog

  As a customer,
  I want to get a list of laptops from the store catalog,
  So that I can be familiar with available models.

  Scenario: Customer requests available laptops from the store catalog
    Given Catalog contains a few laptop models
    When Customer tries to get available models
    Then Customer should get all models
