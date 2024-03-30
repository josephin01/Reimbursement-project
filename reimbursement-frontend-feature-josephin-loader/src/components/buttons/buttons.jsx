import React from "react";
import "./button.scss";

function Button({Data,onClick}) {
  return (
      <button onClick={onClick}
        className={
        Data=='Reject'?'buttonColor reject':
        Data=='Cancel'?'buttonColor cancel':
        'buttonColor'
        }
      >
        {Data}
      </button>
  );
}

export default Button;
