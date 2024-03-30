import React from "react";
import { Bar } from "react-chartjs-2";
import "./progressChart.scss";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Tooltip,
  Legend,
} from "chart.js";
ChartJS.register(CategoryScale, LinearScale, BarElement, Tooltip, Legend);

function Progress({ expenseTypeAmount }) {
  const data = {
    labels: expenseTypeAmount.map((item) => item.expense),
    datasets: [
      {
        data: expenseTypeAmount.map((item) => item.amount_spent),
        backgroundColor: [
          "#FF6384",
          "#36A2EB",
          "#FFCE56",
          "#FF5733",
          "#4BC0C0",
          "red",
        ],
        hoverBackgroundColor: [
          "#FF6384",
          "#36A2EB",
          "#FFCE56",
          "#FF5733",
          "#4BC0C0",
          "red",
        ],
      },
    ],
  };
  const options = {
    indexAxis: "y",
    scales: {
      x: {
        grid: {
          display: false,
        },
      },
      y: {
        grid: {
          display: false,
        },
      },
    },
    plugins: {
      legend: {
        display: false,
        responsive: true,
        position: "right",
        align: "center",
      },
      label: {
        display: false,
      },
    },
  };

  return <Bar data={data} options={options} />;
}

export default Progress;
