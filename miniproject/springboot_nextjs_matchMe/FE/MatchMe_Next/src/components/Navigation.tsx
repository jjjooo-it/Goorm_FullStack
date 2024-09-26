"use client";

import { useState, useEffect } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useAuth } from "@/app/context/AuthContext";

const Navigation = () => {
  const { isAuthenticated, logout } = useAuth();
  const router = useRouter();

  const handleLogout = () => {
    logout();
    router.push("/login");
  };

  return (
    <nav className=" bg-gray-800 p-4">
      <ul className="text-xl flex space-x-10 pl-10">
        <li>
          <Link className="text-white hover:text-gray-300" href={"/"}>
            홈
          </Link>
        </li>

        {isAuthenticated ? (
          <li>
            <button
              className="text-white hover:text-gray-300"
              onClick={handleLogout}
            >
              로그아웃
            </button>
          </li>
        ) : (
          <li>
            <Link className="text-white hover:text-gray-300" href={"/login"}>
              로그인
            </Link>
          </li>
        )}

        {!isAuthenticated && (
          <li>
            <Link className="text-white hover:text-gray-300" href={"/signup"}>
              회원가입
            </Link>
          </li>
        )}
      </ul>
    </nav>
  );
};

export default Navigation;
