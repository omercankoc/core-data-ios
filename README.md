# iOS Core Data

Use Core Data to save your application’s permanent data for offline use, to cache temporary data, and to add undo functionality to your app on a single device. To sync data across multiple devices in a single iCloud account, Core Data automatically mirrors your schema to a CloudKit container.

Through Core Data’s Data Model editor, you define your data’s types and relationships, and generate respective class definitions. Core Data can then manage object instances at runtime to provide the following features.

## Scenario
- When the application is opened, if there is recorded data, show the saved data.
- If the user clicks on one of the saved data, go to the detail page of the relevant data.
- Delete data with a swipe gesture.
- Create a new record with the help of the navigation bar.
