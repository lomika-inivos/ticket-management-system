import React from "react";
import { startSystem, stopSystem } from "../services/api";
import TicketCount from "./TicketCount";

/**
 * Controls component for managing the start and stop actions of a system.
 *
 * @param {Object} props - The properties object.
 * @param {boolean} props.systemRunning - Indicates whether the system is currently running.
 * @param {Function} props.onSystemStart - Callback function to be called when the system starts.
 * @param {Function} props.onSystemStop - Callback function to be called when the system stops.
 *
 * @returns {JSX.Element} The rendered Controls component.
 */
const Controls = ({ systemRunning, onSystemStart, onSystemStop }) => {
  const handleStart = async () => {
    try {
      await startSystem();
      onSystemStart();
    } catch (error) {
      alert("Failed to start the system: " + error.message);
    }
  };

  const handleStop = async () => {
    try {
      await stopSystem();
      onSystemStop();
    } catch (error) {
      alert("Failed to stop the system: " + error.message);
    }
  };

  return (
    <div className="text-center">
        <TicketCount/>
        <div className="py-3"></div>
      {!systemRunning && (
        <button onClick={handleStart} className="btn btn-success mb-3">
          Start System
        </button>
      )}
      {systemRunning && (
        <button onClick={handleStop} className="btn btn-danger">
          Stop System
        </button>
      )}
    </div>
  );
};

export default Controls;
