# Totp_Demo
## Project Overview
The Totp_Demo project is designed to provide user authentication using TOTP-based OTP verification. It includes four main RESTful APIs that manage user registration, OTP generation, and OTP validation using a centralized H2 database.

## Database Structure
### 1. users Table
This table stores details about the users.

#### Fields:
username: String (Primary Key)
datetime: LocalDateTime (Timestamp of registration/updates)
password: String (Encrypted password)
user_status: String (e.g., "active", "attempt1", "attempt2", "locked")

### 2. activates Table
This table is responsible for storing OTP-related details and secrets for registered users.

#### Fields:
username: String (Primary Key)
datetime: LocalDateTime (Timestamp of OTP activation)
OTP: String (Current OTP)
password: String (Encrypted password in Base64)
secret: String (16-digit secret generated for TOTP)

## API Details

### 1. API: /usersInit
This API is responsible for creating or unlocking a user account in the users table.

HTTP Method: POST

URL: /usersInit

#### Request Body:

json

{
  "username": "user1",
  "password": "pass123"
}

#### Behavior:

Insert a new user into the users table.
If the user already exists and is locked, this API can unlock the user account by setting user_status back to active.
Set the datetime field to the time of the user creation.

#### Response:

Success message with the created or updated username.
Status code: 200 (OK)

#### Example Response:

json
{
  "message": "User created/updated successfully",
  "username": "user1"
}


### 2. API: /activateOtp
This API is used to activate or reactivate a user in the activates table and generate a secret for OTP.

HTTP Method: POST

URL: /activateOtp

#### Request Body:

json
{
  "username": "user1",
  "password": "pass123",
  "deviceId": "deviceId123"
}

#### Behavior:

Check if the user exists in the activates table.
If present, reactivate the user and regenerate the secret using the username, deviceId, and password.
If not present, validate the user in the users table, then activate and insert the user into the activates table with a newly generated secret.
If user credentials are incorrect, update user_status in the users table to:
attempt1 for the first invalid attempt,
attempt2 for the second invalid attempt,
locked after the third invalid attempt.
If the user is locked, the response will indicate that the account is locked.

#### Response:

On success, return the username, message, and the generated secret.
On failure (locked or incorrect credentials), return appropriate messages with username.

#### Example Success Response:

json
{
  "username": "user1",
  "secret": "ABCD1234EFGH5678",
  "message": "User activated successfully"
}

#### Example Locked Response:

json
{
"username": "user1",
"secret": "",
  "message": "Your account is locked, kindly reset the account"
}

### 3. API: /generateOTP
This API generates a time-based OTP using the secret for the user and is valid for 60 seconds.

HTTP Method: POST

URL: /generateOTP

#### Request Body:

json
{
  "username": "user1",
  "secret": "ABCD1234EFGH5678"
}

#### Behavior:

Check if the user exists in the activates table.
Generate a time-based OTP using the given secret.
Return the OTP in the response.

#### Response:

Return the OTP.

#### Example Response:

json
{
  "otp": "123456"
}

### 4. API: /validateOtp
This API is used to validate the OTP provided by the user.

HTTP Method: POST

URL: /validateOtp

#### Request Body:

json
{
  "username": "user1",
  "otp": "123456"
}

#### Behavior:

Retrieve the secret from the activates table.
Generate the OTP using the same logic as the /generateOTP API.
Compare the generated OTP with the provided OTP.
On a successful match, return an authentication success response.
If the OTP mismatches, update the user_status in the users table:
attempt1 for the first invalid attempt,
attempt2 for the second invalid attempt,
locked after the third invalid attempt.

#### Response:

Success: Authentication successful with a message.
Failure: Authentication failed with the number of invalid attempts.

#### Example Success Response:

json
{
  "username": "user1",
  "authentication": true,
  "message": "User authenticated successfully"
}

#### Example Failure Response:

json
{
  "username": "user1",
  "authentication": false,
  "message": "First invalid attempt"
}

## Key Notes
H2 Database Configuration: The application uses an in-memory H2 database for simplicity.
Account Locking: After 3 invalid attempts (either for password or OTP), the user account is locked, and no further actions can be performed without resetting the account.
