import React, { useEffect, useState } from "react";
import { connectToWebSocket } from "../services/api";

/**
 * TicketCount component displays the real-time count of tickets.
 * 
 * This component connects to a WebSocket endpoint to receive updates
 * on the ticket count and displays the count in a card layout.
 * 
 * @component
 * @example
 * return (
 *   <TicketCount />
 * )
 * 
 * @returns {JSX.Element} A card displaying the real-time ticket count.
 */
const TicketCount = () => {
  const [ticketCount, setTicketCount] = useState(0);

  useEffect(() => {
    const subscription = connectToWebSocket("/topic/ticket-count", (message) => {
      setTicketCount(message); // Update the ticket count with the parsed message data
    });

    return () => subscription.unsubscribe(); // Cleanup subscription on unmount
  }, []);

  return (
    <div className="card text-center mt-3">
      <div className="card-body">
        <h4>Real-Time Ticket Count</h4>
        <h1>{ticketCount}</h1>
      </div>
    </div>
  );
};

export default TicketCount;
