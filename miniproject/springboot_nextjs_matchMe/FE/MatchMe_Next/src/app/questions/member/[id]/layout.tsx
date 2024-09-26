"use client";

import { ReactNode, useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { useAuth } from "@/app/context/AuthContext";

const QuestionListByMemberLayout = ({children}: {children: ReactNode}) => {
  const { isAuthenticated } = useAuth();
  const router = useRouter();
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    if (isAuthenticated === false) {
      // 인증되지 않았을 경우 login 페이지로 리다이렉트
      alert("로그인이 필요한 페이지입니다.");
      router.push("/login");
    } else if (isAuthenticated === true) {
      // 인증된 경우 로딩 상태 종료
      setIsLoading(false);
    }
  }, [isAuthenticated, router]);

  // 로딩 중일 때 로딩 메시지 표시
  if (isAuthenticated === null || isLoading) {
    return <div>로딩 중...</div>; // 로딩 중일 때 렌더링할 메시지
  }

  return (
    <>
      {children}
    </>
  )
}

export default QuestionListByMemberLayout