import React, { useState, useEffect } from "react";
import ConfigureForm from "./components/ConfigureForm";
import Controls from "./components/Controls";
import LogsDisplay from "./components/LogsDisplay";
import { getConfig } from "./services/api";

const App = () => {
  const [systemRunning, setSystemRunning] = useState(false);
  const [currentConfig, setCurrentConfig] = useState(null);

  // Fetch the current configuration on load
  useEffect(() => {
    const fetchConfig = async () => {
      try {
        const config = await getConfig();
        setCurrentConfig(config);
      } catch (error) {
        alert("Failed to fetch configuration: " + error.message);
      }
    };

    fetchConfig();
  }, []);

  const handleSystemStart = () => setSystemRunning(true);
  const handleSystemStop = () => setSystemRunning(false);

  return (
    <div className="container mt-5">
      <h1 className="text-center mb-4">🎫 Ticketing System Dashboard</h1>
      <div className="row">
        {/* Left Column: Configuration */}
        <div className="col-md-6">
          <div className="card shadow-sm mb-4">
            <div className="card-body">
              <h2 className="card-title text-center py-2">Configuration</h2>
              {currentConfig ? (
                <div className="mb-3">
                  <h5>Current Configuration</h5>
                  <ul className="list-group">
                    <li className="list-group-item">Vendors: {currentConfig.vendorCount}</li>
                    <li className="list-group-item">Customers: {currentConfig.customerCount}</li>
                    <li className="list-group-item">Max Ticket Capacity: {currentConfig.maxTicketCapacity}</li>
                    <li className="list-group-item">Total Tickets per Vendor: {currentConfig.totalTickets}</li>
                    <li className="list-group-item">Total Tickets per Customer: {currentConfig.ticketsPerCustomer}</li>
                    <li className="list-group-item">Ticket Release Rate: {currentConfig.ticketReleaseRate} sec</li>
                    <li className="list-group-item">Customer Retrieval Rate: {currentConfig.customerRetrievalRate} sec</li>
                  </ul>
                </div>
              ) : null}
                <ConfigureForm
                  setCurrentConfig={setCurrentConfig}
                />
            </div>
          </div>
        </div>

        {/* Right Column: Controls and Logs */}
        <div className="col-md-6">
          <div className="card shadow-sm mb-4">
            <div className="card-body text-center">
              <Controls
                systemRunning={systemRunning}
                onSystemStart={handleSystemStart}
                onSystemStop={handleSystemStop}
              />
            </div>
          </div>
          {systemRunning && (
            <div className="card shadow-sm">
              <div className="card-body">
                <h5 className="card-title">Real-Time Logs</h5>
                <LogsDisplay />
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default App;
