export const validation = (name, value, errors, setErrors) => {
  const trimmedValue = value.trim();
  const updatedErrors = { ...errors };

  switch (name) {
    case "firstName":
      updatedErrors.firstName = !/^[a-zA-Z]+$/.test(trimmedValue)
        ? "First name must contain only letters and no spaces."
        : "";
      break;
    case "lastName":
      updatedErrors.lastName = !/^[a-zA-Z]+$/.test(trimmedValue)
        ? "Last name must contain only letters and no spaces."
        : "";
      break;
    case "contact":
      updatedErrors.contact = !/^\d{10}$/.test(trimmedValue)
        ? "Contact must contain exactly 10 numeric digits."
        : "";
      break;
    case "email":
      updatedErrors.email =
        !/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/.test(trimmedValue)
          ? "Invalid email address."
          : "";
      break;
    case "empId":
      updatedErrors.empId = !/^\d+$/.test(trimmedValue)
        ? "EmployeeID must contain only numeric characters."
        : "";
      break;
    default:
      break;
  }

  setErrors(updatedErrors);

  for (const error in updatedErrors) {
    if (updatedErrors[error]) {
      return false;
    }
  }
  return true;
};
