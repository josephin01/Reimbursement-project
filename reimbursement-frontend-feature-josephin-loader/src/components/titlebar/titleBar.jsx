import React  from "react";
import { CiCalendarDate } from "react-icons/ci";
import { IoNotificationsOutline } from "react-icons/io5";
import Notification from "../../components/notification/notification";
import { FaPowerOff } from "react-icons/fa6";
import "./titleBar.scss";
import { oAUthlogout } from "../../action/authAction";

function TitleBar({ setNotification, notification, data }) {
  const date = new Date();
  const today = `${date.getDate()}/${
    date.getMonth() + 1
  }/${date.getFullYear()}`;

  const handleClick = () => {
    setNotification(true)
  };
  return (
    <div className="titleBar">
      <div className="container">
        <div className="titleDate">
          <CiCalendarDate className="titleBar-icon" />
          {today}
        </div>
        <div className="notificationIcon">
          <IoNotificationsOutline onClick={handleClick} className="titleBar-icon"  />
          <div className="notificationCount">
            {data && (data.length > 0 ? data.length : null)}
          </div>
        </div>
        <div>
          <FaPowerOff className="titleBar-icon" onClick={oAUthlogout} />
        </div>
      </div>
    </div>
  );
}

export default TitleBar;