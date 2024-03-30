import React, { useState,useEffect,useRef  } from "react";
import { Outlet } from "react-router-dom";
import SideBar from "../../components/sidebar/sideBar";
import TitleBar from "../../components/titlebar/titleBar";
import "../layout.scss";
import { adsidebar} from "../../assets/mockData/mockData";
import Notification from "../../components/notification/notification";
import { adminNotification } from "../../action/api/Api_config";


function Admin() {
  const [side, setSide] = useState("Home");
  const [notification, setNotification] = useState(false);
  const [notificationData,setNotificationData] = useState();
  const notificationRef = useRef(null);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (notificationRef.current && !notificationRef.current.contains(event.target)) {
        setNotification(false);
      }
    };
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);


  useEffect(() => {
    const intervalId = setInterval(() => {
      adminNotification(setNotificationData);
    }, 10000); 

    return () => clearInterval(intervalId);
}, []);

  return (
    <div className="Home">

      <div className="SideBar">
        <SideBar Data={adsidebar} side={side} setSide={setSide} />
      </div>
      <div className="titlebar-outlet">
        <div className="titleBarContainer">
          <TitleBar
            setNotification={setNotification}
            notification={notification}
            data={notificationData}
          />
        </div>
        {notification ? (
          <div className="notificationContainer" ref={notificationRef}>
            <Notification data={notificationData}/>
          </div>
        ) : null}
        <div className="outletContainer">
          <Outlet />
        </div>
      </div>
    </div>
  );
}

export default Admin;