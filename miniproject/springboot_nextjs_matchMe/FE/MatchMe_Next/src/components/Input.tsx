import { ChangeEvent } from "react";

interface InputProps {
  type?: "text" | "password" | "number" | "email";
  id?: string;
  name?: string;
  placeholder?: string;
  value: string;
  onChange?: (e: ChangeEvent<HTMLInputElement>) => void;
  required?: boolean;
}

const Input = ({
  type,
  placeholder,
  name,
  value,
  onChange,
  required,
}: InputProps) => {
  return (
    <input
      type={type === undefined ? "text" : type}
      placeholder={placeholder}
      name={name}
      value={value}
      onChange={onChange}
      className="border border-gray-300 rounded-lg px-5 py-4 text-black mb-5 w-full focus:outline-none focus:border-gray-300"
      required={required ? true : false}
    />
  );
};

export default Input;
