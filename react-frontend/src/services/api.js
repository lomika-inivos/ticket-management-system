import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

const API_BASE_URL = "http://localhost:3001/api/v1";

/**
 * Connects to a WebSocket server and subscribes to a specified topic.
 *
 * @param {string} topic - The topic to subscribe to.
 * @param {function} callback - The callback function to handle incoming messages.
 * @param {boolean} isFromlogs - Flag to determine if the message is from logs.
 * @returns {Object} An object with an `unsubscribe` method to close the WebSocket connection.
 */
export const connectToWebSocket = (topic, callback, isFromlogs) => {
  const socket = new SockJS("http://localhost:3001/ws");
  const stompClient = new Client({
    webSocketFactory: () => socket,
    reconnectDelay: 5000, // Automatically reconnect after 5 seconds
    onConnect: () => {
      stompClient.subscribe(topic, (message) => {
        try {
          if (isFromlogs) {
            callback(message.body);
            return;
          }
          const data = JSON.parse(message.body); // Parse the JSON body
          

          callback(data); // Pass the parsed data to the callback
        } catch (error) {
          console.error("Error parsing message body:", error);
        }
      });
    },
    onDisconnect: () => {
      console.log("Disconnected from WebSocket");
    },
    onStompError: (frame) => {
      console.error("STOMP error", frame);
    },
  });

  stompClient.activate();

  return {
    unsubscribe: () => {
      if (stompClient) {
        stompClient.deactivate(); // Properly close WebSocket connection
      }
    },
  };
};

/**
 * Starts the system with the given configuration.
 *
 * @param {Object} config - The configuration object to start the system.
 * @returns {Promise<void>} - A promise that resolves when the system is started.
 * @throws {Error} - Throws an error if the system fails to start.
 */
export const startSystem = async (config) => {
  const response = await fetch(`${API_BASE_URL}/simulation/start`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(config),
  });

  if (!response.ok) {
    throw new Error("Failed to start the system");
  }
};

/**
 * Sends a POST request to stop the system simulation.
 * 
 * @throws {Error} Throws an error if the request fails.
 * @returns {Promise<void>} A promise that resolves when the system is successfully stopped.
 */
export const stopSystem = async () => {
  const response = await fetch(`${API_BASE_URL}/simulation/stop`, {
    method: "POST",
  });

  if (!response.ok) {
    throw new Error("Failed to stop the system");
  }
};

/**
 * Fetches the configuration from the API.
 *
 * @async
 * @function getConfig
 * @returns {Promise<Object>} The configuration data as a JSON object.
 * @throws {Error} If the fetch operation fails.
 */
export const getConfig = async () => {
  const response = await fetch(`${API_BASE_URL}/config`);
  if (!response.ok) {
    throw new Error("Failed to fetch configuration");
  }
  return response.json();
};

/**
 * Saves the given configuration to the server.
 *
 * @param {Object} config - The configuration object to be saved.
 * @throws {Error} Throws an error if the configuration could not be saved.
 * @returns {Promise<void>} A promise that resolves when the configuration is successfully saved.
 */
export const saveConfig = async (config) => {
  const response = await fetch(`${API_BASE_URL}/config`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(config),
  });
  if (!response.ok) {
    throw new Error("Failed to save configuration");
  }
};
