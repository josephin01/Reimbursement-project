import React from 'react';
import "./statusCards.scss";
import { FaUserCheck } from 'react-icons/fa';
import { TbFileScissors } from "react-icons/tb";

const Card=({cardsData,isCardSelect,setIsCardSelect,cardCount}) => {
    const iconMap = {
    'PENDING': <FaUserCheck />,
    'MANAGER_APPROVED': <FaUserCheck />,
    'MANAGER_REJECTED': <TbFileScissors/>,
    'ADMIN_APPROVED': <FaUserCheck />,
    'ADMIN_REJECTED': <TbFileScissors/>
  };
  
  const handleClick=(title)=>{
    setIsCardSelect(title)
  }
  
  return (
    <div className='card-container'>
      {cardCount.map((card, index) => (
        <div key={index} className='card' onClick={()=>handleClick(card.expense_status)}>
          <div className='icon-container card-icon'>
            {iconMap[card.expense_status]}
          </div>
          <p className='card-text'>{card.expense_status.replace(/_/g," ")}</p>
          <p className='card-count'>{card.expense_status_count}</p>
        </div>
      ))}
    </div>
  );
};

export default Card;