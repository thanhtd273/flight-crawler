package com.thanhtd.flight.storage.constant;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SUCCESS(200, "Success"),
    FAIL(404, "Fail"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    LOGIN_FAIL(401, "Login Failed"),
    SIGNUP_FAIL(401, "Signup Failed"),
    WRONG_PASSWORD(400, "Old password does not match with current password"),
    NO_SESSION(401, "No user in this session. You must login to create access resource"),
    EXPIRED_CODE(404, "Code is expired"),
    INVALID_CODE(404, "Invalid code"),
    INVALID_DATA_FORMAT(400, "Invalid data format"),
    INVALID_EMAIL(400, "Invalid email"),
    INVALID_DATE(400, "Invalid date"),
    INVALID_TOKEN(401, "Invalid Token"),
    VALID_TOKEN(201, "Valid token"),
    ID_NULL(400, "ID is null"),
    NULL_VALUE(404, "Value is null"),
    DATA_NULL(404, "Data is null"),
    BLANK_FIELD(404, "The require field is blank"),
    NOT_FOUND_CITY(404, "City does not exist"),
    NOT_FOUND_USER(404, "User does not exist"),
    NOT_FOUND_ROLE(404, "Role does not exist"),
    NOT_FOUND_PERMISSION(404, "Permission does not exist"),
    NOT_FOUND_ACTION(404, "Action does not exist"),
    NOT_FOUND_RESOURCE(404, "Resource does not exist"),
    CALL_API_ERROR(404, "Call external API fail");

    final int value;
    final String message;

    private ErrorCode(int value, String message) {
        this.value = value;
        this.message = message;
    }

}
