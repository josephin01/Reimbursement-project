import '../input/input.scss';

const input = (props) => {
    const {
      displayName,
      placeholder,
      id,
      value,
      type,
      handleBlur,
      errorMessage,
      handleChange
    } = props;
    return (
      <div className='input-container'>
        {displayName}
        <input
          id={id}
          placeholder={placeholder}
          onChange={handleChange}
          value={value}
          required={true}
          type={type ? type : "text"}
          onBlur={handleBlur}
        />
        {errorMessage && <p style={{color:"red"}}> {errorMessage}</p>}
      </div>
    );
  };
  export default input;