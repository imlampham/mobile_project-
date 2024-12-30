# midterm-mobile-prg

![image](https://github.com/user-attachments/assets/5eabea97-f327-4116-80b4-702a4561f8a6)

## ~~Warning: This code violates some security practice~~
- ~~I hardcode my API key inside this code~~
- ~~This API key has not been configured to limit its permission~~

~~Therefore, this code should not be published/make public without modification.~~
Fixed.

## Spreadsheet link
[https://docs.google.com/spreadsheets/d/1ZF4XdUqO_nR1cIkAxppI6mRXoMcqV3PGszgddYyKK-8/edit?gid=0#gid=0](https://docs.google.com/spreadsheets/d/1ZF4XdUqO_nR1cIkAxppI6mRXoMcqV3PGszgddYyKK-8/edit?gid=0#gid=0)

This code currently only supports 2 columns -> need to reorganize and select the final sheets layout

## Project Outline
### 1. **Set Up Google Sheets as Backend** (done)

- Create a Google Form to collect user feedback.
- Link the form to a Google Sheet to store responses automatically.
- Set up the Google Sheets API to allow your app to access the sheet data.

### 2. **Enable Google Sheets API and Obtain API Key** (done)

- Go to Google Cloud Console, enable the Google Sheets API, and create a project.
- Set up OAuth 2.0 credentials or an API key for your app to access Google Sheets.
- Make sure to configure access permissions for the Google Sheet (e.g., make it publicly accessible or set up authentication).

### 3. **Integrate Google Sheets API in Your Kotlin Project** (done)

- Use a networking library like Retrofit or OkHttp to make API calls.
- Use the Google Sheets API endpoint to fetch data from your sheet.
- Parse the JSON response to extract the relevant data you want to display in the app.

### 4. **Fetch Data and Display It** (currently look ugly)

- In your app, set up a method to fetch data from Google Sheets when the app launches or a button is clicked.
- Display the fetched data in your app's UI (e.g., in a `RecyclerView`).

### 5. **Save Data to SQLite for Offline Access** (done)

- Set up a local SQLite database in your app.
- After fetching data from Google Sheets, save it to SQLite.
- Create methods for inserting, updating, and querying data from the SQLite database.
- The data is cleared every time call insert() - need fixing

### 6. **Implement Offline Functionality** (done)

- When the app launches or the fetch button is clicked, check if there is an internet connection.
- If there is no internet connection, load data from the SQLite database.
- If there is an internet connection, fetch fresh data from Google Sheets, update the display, and store it in SQLite.

### 7. **Additional Considerations** (have not done anything)

- **Error Handling:** Handle potential errors when fetching data or saving to the database.
- **User Interface:** Consider a loading indicator when data is being fetched.
- **Testing:** Test both online and offline scenarios to ensure smooth transitions and functionality.
