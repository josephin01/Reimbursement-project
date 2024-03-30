import React,{useState,useContext} from "react";
import "./travelFormCards.scss";
import { IoIosArrowForward } from "react-icons/io";
import { travelFormCards } from "../../../constants/constant";
import { useNavigate } from "react-router-dom";
import { AppContext } from "../../../context/context";

function TravelformCards({containerData}) {
  
  const navigate = useNavigate();
  const { travelFormStatus } = useContext(AppContext);
  const [travelFormStatusValue, setTravelFormStatusValue] = travelFormStatus;
  const handleClick=(s)=>{
    setTravelFormStatusValue(s)
    navigate("AdminTravelForm");
  }
  return (
    <div className="container-wrapper">
      <div className="cardTitle">{travelFormCards.Travel_Form}</div>
      {containerData.map((data, index) => (
        <div className="main-container" key={index} onClick={()=>handleClick(data.travel_form_status)}>
          <div className="header">
            <div className="description">
              <header className="title">{data.travel_form_status}</header>
              {data.travel_form_status_count}
            </div>
            <div className="arrow-icon">
              <IoIosArrowForward className="arrow" />
            </div>
          </div>
        </div>
      ))} 
    </div>
  );
}

export default TravelformCards;
