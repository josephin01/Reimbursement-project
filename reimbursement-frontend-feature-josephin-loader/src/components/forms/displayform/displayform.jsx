import React from 'react';
import './displayform.scss';
import Bill from '../../../components/bill/viewBill/viewBill';

function DisplayForm({ Data, headers }) {
  return (
    <div className='formContainer'>
      <div className='grid-container'>
        {headers.map((value, key) => (
          <div key={key} className="grid-item">
            <label className='displayLabel'>{value.label}:</label>
            <span className='displaySpan'>{
              value.key === 'colleagueDetails' && Data[value.key] ?
                JSON.parse(Data[value.key]).map(colleague => colleague.firstName).join(', ') :
                (value.key === "expenseType" && Data[value.key]) ? 
                  Data[value.key].expenses
                  :
                  Data[value.key]
            }</span>
          </div>
        ))}
      </div>
      <div className='billContainer1'>
        {Data.bills && Data.bills.map((bill, index) => (
          <div key={index}>
            <Bill bill={bill} />
          </div>
        ))}
      </div>
    </div>
  );
}

export default DisplayForm;
