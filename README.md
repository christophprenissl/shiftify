# Shiftify
A app for planing the best possible shifts for everyone in a nurse station

## Description
A user can register and login as a nurse or as a shift owner.
When choosing shift owner you have to provide the name of the station with nurses you manage.
When choosing nurse you have to type in the station name you are working at.

### Nurse
As a nurse you can see the current month as a calendar view. When tapping on the previous or next
button the user can switch between months. When choosing a day of the month the nurse can
set the shift priorities by dragging and dropping a shift card. Drops on upper fields means a higher
priority. Drops on the lower fields mean lesser prioritization.
When the user chooses ok the priorities gets saved and the user is navigated back to the calendar view.
When tapping on ok & next the next day gets loaded with its priorities to be set.
Cancel lets the user undo the changes and navigates it back to calendar view.
In calendar view the user can use the options menu to logout which will lead to a navigation back to
the login screen.

### Shift Owner
When logged in as a shift owner the user gets prompted to choose her role as a shift owner but also
as a nurse. When nurse is chosen the user gets navigated to the NurseOverview and has all possibilities
like logged in as a nurse.
When choosing shift owner the app navigates to a different calendar view where the user can navigates the
months as in the calendar view of the NurseOverview.
When tapping on a day in the current month a list of all the assigned nurses to the shift owner's station
gets displayed with all the set shift priorities for that day.

## Assignment features for course Android App Development

### Components
- Use of Google Auth for register a user or login with email and password
- Implementation of a own calendar view with use of Recycler Views with 
  adapters for displaying calendar days which are MaterialCards
- Use of Material Buttons and MaterialCardViews
- Implementation of a drag and drop mechanism for moving views

### Persistence 
- Use of MutableLiveData to store states and as a wrapper for loaded database content
- Use of Firebase Realtime Database references to store and receive data
- Use of shared activity view models across several fragments

### UX
- Use of Navigation Component for navigating the app with use of fragments
- Use of states listener to handle navigation
- Use of options menu to handle logout of user
- Use of dark mode / light mode themes which are triggered by system preferences

### DX
- Use of material components and design patterns of Google's material.io
- Use of MVVM pattern
- Use of Mapper classes for entity and dto objects

### Git
- Use of gitflow pattern for branching
- Use of tagged releases
