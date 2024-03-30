import React, { useState, useEffect } from "react";
import "../imageSlider/imageSlider.scss";
import image1 from "../../assets/images/loginImage1.svg";
import image2 from "../../assets/images/loginImage2.svg";
import image3 from "../../assets/images/loginImage3.svg";
import image4 from "../../assets/images/loginImage4.svg";

const ImageSlider = () => {
  const [currentImageIndex, setCurrentImageIndex] = useState(0);
  const [isAutoSliding, setIsAutoSliding] = useState(true);
  const imageSlides = [image1, image2, image3, image4];
  const handleNext = () => {
    setCurrentImageIndex((prevIndex) => (prevIndex + 1) % imageSlides.length);
  };

  const handlePrevious = () => {
    setCurrentImageIndex((prevIndex) =>
      prevIndex === 0 ? imageSlides.length - 1 : prevIndex - 1
    );
  };

  useEffect(() => {
    let intervalId;

    if (isAutoSliding) {
      intervalId = setInterval(() => {
        setCurrentImageIndex(
          (prevIndex) => (prevIndex + 1) % imageSlides.length
        );
      }, 2000);
    }

    return () => clearInterval(intervalId);
  }, [isAutoSliding, imageSlides.length]);

  return (
    <div>
      <img src={imageSlides[currentImageIndex]} alt="" />

      <div className="snippet" data-title="dot-flashing">
        <div className="stage">
          <div className="dot-flashing" onClick={handleNext}></div>
        </div>
      </div>
    </div>
  );
};

export default ImageSlider;
