import React, { useEffect, useState } from "react";
import { connectToWebSocket } from "../services/api";

/**
 * LogsDisplay component subscribes to a WebSocket topic to receive log messages in real-time.
 * It maintains a list of logs in its state and displays them in a scrollable container.
 *
 * @component
 * @example
 * return (
 *   <LogsDisplay />
 * )
 *
 * @returns {JSX.Element} A React component that displays log messages.
 */
const LogsDisplay = () => {
  const [logs, setLogs] = useState([]);

  useEffect(() => {
    const subscription = connectToWebSocket("/topic/logs", (message) => {
    
      setLogs((prevLogs) => [...prevLogs, message]);
    },true);

    return () => subscription.unsubscribe();
  }, []);

  return (
    <div style={{ maxHeight: "300px", overflowY: "auto", border: "1px solid #ddd", borderRadius: "5px", padding: "10px", backgroundColor: "#f8f9fa" }}>
      {logs.length === 0 ? (
        <p>No logs available yet.</p>
      ) : (
        logs.map((log, index) => (
          <p key={index} style={{ marginBottom: "5px" }}>
            {log}
          </p>
        ))
      )}
    </div>
  );
};

export default LogsDisplay;
