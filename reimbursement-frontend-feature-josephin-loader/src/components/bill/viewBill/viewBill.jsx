
import React, { useState } from "react";
import Modal from "react-modal";
import { FiDownload } from "react-icons/fi";
import "./viewBill.scss";
import { IoCloseCircleSharp } from "react-icons/io5";

const Bill = ({ bill }) => {
  const [showImage, setShowImage] = useState(false);
  const [selectedBill, setSelectedBill] = useState(null);

  const handleButtonClick = (bill) => {
    setShowImage(true);
    setSelectedBill(bill);
  };
  const handleCloseModal = (event) => {
    const isCloseButtonClicked = event.target.closest(".closeButton");
    if (isCloseButtonClicked) {
      setShowImage(false);
    }
  };

  return (
    <div className="buttonContainer">
      <div
        key={bill.id}
        className="combinedContainer"
        onClick={() => handleButtonClick(bill)}
      >
        <FiDownload className="icon" />
        <span className="billsButton">{bill.billName}</span>
      </div>

      <Modal
        isOpen={showImage}
        onRequestClose={handleCloseModal}
        contentLabel="Image Modal"
        shouldCloseOnOverlayClick={false}
        className="modalContent"
      >
        {selectedBill && (
          <img
            src={selectedBill.billsUrl}
            alt={selectedBill.billType}
            className="modalImage"
          />
        )}
        <button
          onClick={(event) => handleCloseModal(event)}
          className="closeButton"
        >
          <IoCloseCircleSharp className="closeIcon" />
        </button>
      </Modal>
    </div>
  );
};

export default Bill;