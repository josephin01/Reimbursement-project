import { ArcElement, Chart as ChartJS, Legend, Tooltip } from "chart.js";
import React from "react";
import { Doughnut } from "react-chartjs-2";
import "./doughnutChart.scss";
ChartJS.register(ArcElement, Tooltip, Legend);

function DoughnutCharts({cardCount}) {
  const data = {
    datasets: [
      {
        data: cardCount.map((item) => item.expense_status_count),
        backgroundColor: [
          "#4d6d9a",
          "#86b3d1",
          "rgb(175, 220, 255)",
          "#99ced3",
          "rgb(229, 233, 236)",
        ],
        hoverBackgroundColor: [
          "#4d6d9a",
          "#86b3d1",
          "rgb(175, 220, 255)",
          "#99ced3",
          "rgb(229, 233, 236)",
        ],
      },
    ],
    labels: cardCount.map((item) => item.expense_status.replace(/_/g," ")),
  };
  const options = {
    plugins: {
      legend: {
        display: true,
        responsive: true,
        position: "right",
        align: "center",
      },
    },
  };
  return (
    <div className="doughnutChart">
      <Doughnut data={data} options={options} />
    </div>
  );
}

export default DoughnutCharts;
