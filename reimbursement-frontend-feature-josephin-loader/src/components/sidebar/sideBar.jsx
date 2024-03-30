import React, { useEffect, useState } from "react";
import DivumLogo from "../../assets/images/DivumLogo.png";
import { user } from "../../assets/mockData/mockData";
import { useNavigate } from "react-router-dom";
import { RiArrowDropDownLine } from "react-icons/ri";
import { RiArrowDropUpLine } from "react-icons/ri";

function SideBar({ Data, side, setSide }) {
  useEffect(() => {
  }, [side]);
  const [dropDown, setDropDown] = useState(false);
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const navigate = useNavigate();
  const handleClick = (link, title) => {
    setSide(title);
    navigate(link);
  };
  const handleDropDown = (link) => {
    setDropDown(!dropDown);
  };

  const handleMenuClick = () => {
    setSidebarOpen(!sidebarOpen);
  };
  return (
    <div className="SideBarContainer">
      <div className="logoContainer">
      <img className="DivumLogo" src={DivumLogo} alt="Logo" />
      </div>
      <div className="profileContainer" >
        <div className="sidebarProfileContainer">
          <img className="SideBarProfile" src={user.profile} alt="Profile" />
        </div>
        <div className="userName">Hi {user.fName}</div>
      </div>
      <div className="SideBarContent">
        {Data.map((value, key) =>
          value.title == "Manage Content" || value.title == "Team" ? (
            <div>
              <div className="dropDownField" onClick={() => handleDropDown()}>
                <div
                  className={
                    dropDown ? "SideBarField sideBarActive" : "SideBarField"
                  }
                >
                  <div>{value.icon}</div>
                  <div className="sidbarValues">{value.title}</div>
                  <p className="sidbarValues">{value.link}</p>
                </div>
                <div className="dropdownIcon">
                  {!dropDown ? <RiArrowDropDownLine /> : <RiArrowDropUpLine />}
                </div>
              </div>
              {dropDown ? (
                <div className="submenuContainer">
                  {value.submenu.map((val, i) => (
                    <div
                      key={i}
                      onClick={() => handleClick(val.link, val.title)}
                      className={
                        val.title == side
                          ? "SideBarField sideBarActive"
                          : "SideBarField"
                      }
                      
                    >
                      <p>{val.icon}</p>
                    <div className="sidbarValues" >{val.title}</div>  
                    </div>
                  ))}
                </div>
              ) : null}
            </div>
          ) : (
            <div
              className={
                value.title == side
                  ? "SideBarField sideBarActive"
                  : "SideBarField"
              }
              key={key}
              onClick={() => handleClick(value.link, value.title)}
            >
              <div>{value.icon}</div>
              <div className="sidbarValues">{value.title}</div>
            </div>
          )
        )}
      </div>
    </div>
  )
}

export default SideBar;
