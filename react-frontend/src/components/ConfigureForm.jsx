import React from "react";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import { saveConfig } from "../services/api";

/**
 * ConfigureForm component allows users to set up the initial configuration for the ticket management system.
 * 
 * @component
 * @param {function} onSystemStart - Callback function to start the system after configuration is set.
 * @param {function} setCurrentConfig - Function to update the current configuration state.
 * 
 * @example
 * <ConfigureForm onSystemStart={handleSystemStart} setCurrentConfig={updateConfig} />
 * 
 * @returns {JSX.Element} The rendered ConfigureForm component.
 */
const ConfigureForm = ({ onSystemStart, setCurrentConfig }) => {
  const initialValues = {
    vendorCount: 1,
    customerCount: 1,
    maxTicketCapacity: 10,
    ticketsPerCustomer: 1,
    totalTickets: 10,
    ticketReleaseRate: 2,
    customerRetrievalRate: 2,
  };

  const validationSchema = Yup.object({
    vendorCount: Yup.number().min(1, "Must be at least 1").required("Required"),
    customerCount: Yup.number().min(1, "Must be at least 1").required("Required"),
    maxTicketCapacity: Yup.number().min(1, "Must be at least 1").required("Required"),
    totalTickets: Yup.number().min(1, "Must be at least 1").required("Required"),
    ticketsPerCustomer: Yup.number().min(1, "Must be at least 1").required("Required"),
    ticketReleaseRate: Yup.number().min(1, "Must be at least 1 second").required("Required"),
    customerRetrievalRate: Yup.number().min(1, "Must be at least 1 second").required("Required"),
  });

  const handleSubmit = async (values, { setSubmitting }) => {
    try {
      await saveConfig(values);
      setCurrentConfig(values); // Update current config
      onSystemStart();
    } catch (error) {
      alert("Failed to start the system: " + error.message);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <Formik
      initialValues={initialValues}
      validationSchema={validationSchema}
      onSubmit={handleSubmit}
    >
      {({ isSubmitting }) => (
        <Form>
          {[
            { label: "Vendors", name: "vendorCount" },
            { label: "Customers", name: "customerCount" },
            { label: "Max Ticket Capacity", name: "maxTicketCapacity" },
            { label: "Total Tickets per Vendor", name: "totalTickets" },
            { label: "Total Tickets per Customer", name: "ticketsPerCustomer" },
            { label: "Ticket Release Rate (sec)", name: "ticketReleaseRate" },
            { label: "Customer Retrieval Rate (sec)", name: "customerRetrievalRate" },
          ].map(({ label, name }) => (
            <div className="form-group" key={name}>
              <label>{label}</label>
              <Field type="number" name={name} className="form-control" />
              <ErrorMessage name={name} component="div" className="text-danger" />
            </div>
          ))}
          <button
            type="submit"
            className="btn btn-primary btn-block"
            disabled={isSubmitting}
          >
            {isSubmitting ? "Setting..." : "Set Config"}
          </button>
        </Form>
      )}
    </Formik>
  );
};

export default ConfigureForm;
