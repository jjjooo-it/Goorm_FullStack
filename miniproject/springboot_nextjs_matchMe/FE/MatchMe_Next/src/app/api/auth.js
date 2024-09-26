import { useQuery } from "@tanstack/react-query";
import axios from "axios";

const fetchData = async () => {
  const token = localStorage.getItem("jwtToken");
  const response = await axios.get("http://localhost:8080/api/protected-data", {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
};

export default function ProtectedData() {
  const { data, error, isLoading } = useQuery(["protectedData"], fetchData);

  if (isLoading) return <div>로딩 중...</div>;
  if (error) return <div>에러 발생: {error.message}</div>;

  return <div>보호된 데이터: {JSON.stringify(data)}</div>;
}
