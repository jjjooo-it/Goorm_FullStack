import { useAuth } from "@/app/context/AuthContext";

export const ProtectedPage: React.FC = () => {
  const { isAuthenticated, logout } = useAuth();

  if (!isAuthenticated) {
    return <p>로그인이 필요합니다.</p>;
  }

  return (
    <div>
      <h1>보호된 페이지</h1>
      <button onClick={logout}>로그아웃</button>
    </div>
  );
};
