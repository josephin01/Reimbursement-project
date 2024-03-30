import React, { useState } from 'react';
import Multiselect from 'multiselect-react-dropdown';
import Table from '../table/table';
import '../search/search.scss';
import { FaSearch } from "react-icons/fa";


const Search = ({headers,
  Data,
  isView,
  fetchView,
  setShowRow,
  onDelete,
  onEdit,
  searchBase
}) => {
  const [selectedValues, setSelectedValues] = useState([]);
  const [status,setStatus] = useState('ALL')
  

  const onSelect = (selectedList, selectedItem) => {
    setSelectedValues(selectedList);
  }

  const onRemove = (selectedList, removedItem) => {
    setSelectedValues(selectedList);
  }
  const data = selectedValues.length > 0 ? selectedValues : Data;

  return (

    <div className='Search'>
      <div className='search-bar'>
      <Multiselect
        options={Data || []}
        selectedValues={selectedValues}
        onSelect={onSelect}
        onRemove={onRemove}
        displayValue={searchBase}
      className='searchData' />
      <span className='search-searchIcon'><FaSearch /></span>
      </div>
     <Table Data={data}
          headers = {headers}
          isView={isView}
          fetchView={fetchView}
          setShowRow={setShowRow}
          onDelete={onDelete}
          onEdit={onEdit}
          setStatus={setStatus}  />
    </div>
  )
}

export default Search;
