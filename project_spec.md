# **Petfinder API App**

## Table of Contents

1. [App Overview](#App-Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
1. [Build Notes](#Build-Notes)

## App Overview

### Description 

**An app that returns pets up for adoption according to filter criteria users apply**

### App Evaluation

<!-- Evaluation of your app across the following attributes -->

 - **Description**: An app that allows users to search nearby pets up for adoption by type (cat, dog, etc), age, traits (good with other dogs, children, etc), breed, etc
- **Mobile**: Not a uniquely mobile experience, similar to website
- **Story**: Users can browse what pets are up for adoption near them with the added convenience of looking the information whenever they want from their mobile device
- **Market**: For anyone who has a mobile device and is looking for an animal companion
- **Habit**: A user will probably use the app more frequently if they are looking for a pet and users will only consume information on the app 
- **Scope**: The application is a directory of pets users can scroll through in a recycler view to find a pet they're interested in

## Product Spec

### 1. User Features (Required and Optional)

Required Features:

[X] User can search by animal type (cat, dog, bird, rabbit, etc)
[X] User can filter results by breed, size, and gender
[X] User can view images of each animal their search returns in the recycler view along with their basic info such as name, breed, sex

Stretch Features:

- The app can use the user's location to find animal shelters that are close by.
- User can directly contact the animal shelter or rescue group
- User can review and like pet descriptions
- User can share links to their favorite pets on social media
- User can get alerts of new pets in their area that meet their search criteria

### 2. Chosen API(s)

- **https://api.petfinder.com/v2/types/{type}**
  - User can search by animal type (cat, dog, bird, rabbit, etc)
- **https://api.petfinder.com/v2/types/{type}/breeds**
    - User can filter animals by breed
- **https://api.petfinder.com/v2/animals/gender=???**
    - User can filter animals by gender
- **https://api.petfinder.com/v2/animals/age=???**
    - User can filter animals by age
- **https://api.petfinder.com/v2/animals/location=???**
    - User can filter animals by location
- **https://api.petfinder.com/v2/animals/size=???**
    - User can filter animals by size
### 3. User Interaction

Required Feature

[X] **User can select the type, size and sex of animal they want from a menu**
  - => **App will return data that only contains type, size and sex of animal the user is looking for**
    
 

## Wireframes

<!-- Add picture of your hand sketched wireframes in this section -->
<img src="https://i.imgur.com/f3FBABq.png" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Build Notes

Here's a place for any other notes on the app, it's creation 
process, or what you learned this unit!  

For Milestone 2, include **2+ Videos/GIFs** of the build process here!

## License

Copyright **2023** 
**Pod 32:
Brandon Boit
Ben Karanja
Diana Hung
Aycan Bilge
Rafael Diaz**

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
