"use client";

import { MyButton } from "@/components/MyButton";
import { useAuth } from "./context/AuthContext";
import Image from "next/image";

export default function Home() {
  const { isAuthenticated } = useAuth();

  return (
    <div className="flex flex-col items-center justify-center">
      <h1 className="text-7xl font-bold mb-5">나를 맞춰봐 ! MATCH ME</h1>
      {!isAuthenticated && (
        <p className="text-xl mb-5">
          로그인하고 질문을 생성하거나 친구가 낸 문제를 맞춰보세요!
        </p>
      )}
      <div className="mb-20">
        <Image src="/img/guess.png" alt="image" width={200} height={200} />
      </div>

      {!isAuthenticated && (
        <div className="flex space-x-4">
          <MyButton content="로그인하기" route={"/login"} />
          <MyButton content="회원가입하기" route={"/signup"} />
        </div>
      )}
      {isAuthenticated && (
        <div className="flex space-x-4">
          <MyButton content="친구의 취향 맞추기" route={"/search"} />
          {isAuthenticated && (
            <MyButton content="질문 생성하기" route="/create" />
          )}
        </div>
      )}
    </div>
  );
}
