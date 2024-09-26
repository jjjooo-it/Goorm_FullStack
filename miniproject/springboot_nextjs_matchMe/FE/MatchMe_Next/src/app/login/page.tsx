"use client";

import { useMutation } from "@tanstack/react-query";
import { ChangeEvent, FormEvent, useState } from "react";
import { loginUser } from "../api/member";
import { useRouter } from "next/navigation";
import { MyButton } from "@/components/MyButton";
import Input from "@/components/Input";
import { useAuth } from "../context/AuthContext";

interface LoginType {
  username: string;
  password: string;
}

export default function Login() {
  const router = useRouter();

  const { login } = useAuth();

  const [formData, setFormData] = useState<LoginType>({
    username: "",
    password: "",
  });

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const mutation = useMutation({
    mutationFn: loginUser,
    onSuccess: (data: { accessToken: string; refreshToken: string }) => {
      login(data.accessToken);
      alert("로그인에 성공하셨습니다");
      router.push("/");
    },
    onError: (error) => {
      alert("로그인 실패");
      console.error("로그인 실패...", error);
    },
  });

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    mutation.mutate({
      username: formData.username,
      password: formData.password,
    });
  };

  return (
    <div className="flex justify-center items-center bg-[#313492] overflow-hidden">
      <div className="bg-white shadow-lg rounded-lg p-8 max-w-md w-[430px]">
        <h1 className="text-2xl font-bold text-[#313492] text-center mb-6">
          로그인
        </h1>
        <form onSubmit={handleSubmit}>
          <div>
            <label
              htmlFor="username"
              className="block text-2xl text-[#313492] font-medium mb-2"
            >
              아이디
            </label>
            <Input
              type="text"
              id="username"
              name="username"
              onChange={handleChange}
              value={formData.username}
            />
          </div>
          <div>
            <label
              htmlFor="password"
              className="block text-2xl text-[#313492] font-medium mb-2"
            >
              비밀번호
            </label>
            <Input
              type="password"
              id="password"
              name="password"
              onChange={handleChange}
              value={formData.password}
            />
          </div>
          <div className="flex justify-center">
            <MyButton type="submit" content="로그인하기" />
          </div>
        </form>
      </div>
    </div>
  );
}
