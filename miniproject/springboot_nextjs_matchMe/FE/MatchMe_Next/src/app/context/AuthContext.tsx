"use client"

import { jwtDecode } from "jwt-decode";
import React, { createContext, ReactNode, useContext, useEffect, useState } from "react";

// Authentication Context 생성
interface AuthContextType {
  isAuthenticated: boolean | null;
  login: (token: string) => void;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};

// AuthProvider 컴포넌트
export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean | null>(null);

  // JWT 토큰이 localStorage에 있는지 확인하여 로그인 상태 설정
  useEffect(() => {
    const token = localStorage.getItem("accessToken");
    if(token) {
      try {
        const decoded = jwtDecode(token);
        const now = Math.floor(Date.now() / 1000);
        if (decoded.exp! > now) {
          setIsAuthenticated(true); // 토큰 유효할 경우 인증 상태 복구
        } else {
          localStorage.removeItem("accessToken"); // 만료된 토큰 삭제
        }
      } catch (error) {
        console.error("토큰 복구 실패:", error);
      }
    } else {
      setIsAuthenticated(false);
    }
  }, []);

  // 로그인 함수 (토큰 저장)
  const login = (token: string) => {
    localStorage.setItem("accessToken", token);
    setIsAuthenticated(true);
  };

  // 로그아웃 함수 (토큰 제거)
  const logout = () => {
    localStorage.removeItem("accessToken");
    setIsAuthenticated(false);
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
