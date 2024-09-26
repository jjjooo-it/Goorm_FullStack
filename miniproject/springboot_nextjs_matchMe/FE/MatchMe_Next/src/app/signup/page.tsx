"use client";

import { ChangeEvent, FormEvent, useState } from "react";
import { createUser } from "../api/member";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { useRouter } from "next/navigation";
import { MyButton } from "@/components/MyButton";
import Input from "@/components/Input";

interface SignType {
  username: string;
  password: string;
  email: string;
}

export default function SignUp() {
  const router = useRouter();
  const queryClient = useQueryClient();

  const [formData, setFormData] = useState<SignType>({
    email: "",
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
    mutationFn: createUser,
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["userList"],
      });
      alert("회원가입 완료!");
      router.push("/login");
    },
    onError: () => {
      alert("회원가입 실패...");
    },
  });

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    mutation.mutate({
      email: formData.email,
      username: formData.username,
      password: formData.password,
    });
    console.log(formData);
  };

  return (
    <div className="flex justify-center items-center bg-[#313492] overflow-hidden">
      <div className="bg-white shadow-lg rounded-lg p-12 w-[430px]">
        <h1 className="text-4xl font-bold text-[#313492] text-center mb-8">
          회원가입
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
              value={formData.username}
              onChange={handleChange}
              required
            />
          </div>
          <div>
            <label
              htmlFor="pass word"
              className="block text-2xl text-[#313492] font-medium mb-2"
            >
              비밀번호
            </label>
            <Input
              type="password"
              id="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              required
            />
          </div>
          <div>
            <label
              htmlFor="email"
              className="block text-2xl text-[#313492] font-medium mb-2"
            >
              이메일
            </label>
            <Input
              type="email"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              required
            />
          </div>
          <div className="flex justify-center">
            <MyButton content="회원가입하기" type="submit" />
          </div>
        </form>
      </div>
    </div>
  );
}
