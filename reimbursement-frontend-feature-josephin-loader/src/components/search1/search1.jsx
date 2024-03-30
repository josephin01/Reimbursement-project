import React from 'react'
import './search1.scss'
import { IoIosSearch } from "react-icons/io";

function Search({setItems,travelData}) {
    const handleChange = (e) => {
        const value = e.target.value.toLowerCase().trim();
        let matchingNames = [];
        if (value !== '') {
            matchingNames = travelData.filter((data) =>
            (data.employeeName && data.employeeName.toLowerCase().includes(value)) ||
            (data.firstName && data.firstName.toLowerCase().includes(value))
        );
        } else {
          matchingNames = travelData;
        }
    
        setItems(matchingNames);
      };
  return (
    <div className='searchBar'>
        <IoIosSearch />
        <input className='searchInput' onChange={handleChange} />
    </div>
  )
}

export default Search
