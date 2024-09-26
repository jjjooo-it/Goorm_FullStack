"use client";
import React, { useState, FormEvent, useEffect } from "react";
import { useMutation } from "@tanstack/react-query";
import { createQuestion } from "../api/question";
import { useRouter } from "next/navigation";
import { jwtDecode } from "jwt-decode";
import AnswerOption from "@/components/AnswerOption";
import classNames from "classnames";

interface Answer {
  content: string;
  correct: boolean;
}

interface DecodedToken {
  userId: number;
  memberId: number;
}

interface CreateType {
  content: string;
  answers: Answer[];
  memberId: number;
}

function getUserFromToken(): DecodedToken | null {
  const token = localStorage.getItem("accessToken");
  if (!token) return null;

  try {
    const decoded: DecodedToken = jwtDecode(token);
    return decoded;
  } catch (error) {
    console.error("토큰 디코딩 실패:", error);
    return null;
  }
}

export default function Create() {
  const router = useRouter();

  // 여러 개의 질문을 관리하기 위한 상태
  const [questions, setQuestions] = useState<CreateType[]>([
    {
      content: "",
      answers: [
        { content: "", correct: false },
        { content: "", correct: false },
      ],
      memberId: 0,
    },
  ]);

  console.log(typeof questions);
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0); // 현재 슬라이드 인덱스

  // 유저 토큰에서 memberId 추출
  useEffect(() => {
    const user = getUserFromToken();
    if (user) {
      setQuestions((prev) =>
        prev.map((question) => ({
          ...question,
          memberId: user?.userId,
        }))
      );
    }
  }, []);

  // Answer 추가 함수
  const handleAddAnswer = () => {
    const updatedQuestions = [...questions];
    if (updatedQuestions[currentQuestionIndex].answers.length < 4) {
      updatedQuestions[currentQuestionIndex].answers.push({
        content: "",
        correct: false,
      });
      setQuestions(updatedQuestions);
    }
  };
  
  // Answer 변경 핸들러
  const handleAnswerChange = (index: number, field: string, value: string | boolean) => {
    const updatedQuestions = [...questions];
    updatedQuestions[currentQuestionIndex].answers[index] = {
      ...updatedQuestions[currentQuestionIndex].answers[index],
      [field]: value,
    };
    setQuestions(updatedQuestions);
  };

  // Correct (정답) 선택 핸들러
  const handleCorrectChange = (index: number) => {
    const updatedQuestions = [...questions];
    updatedQuestions[currentQuestionIndex].answers = updatedQuestions[currentQuestionIndex].answers.map((answer, i) => ({
      ...answer,
      correct: i === index,
    }));
    setQuestions(updatedQuestions);
  };

  // 질문 입력 핸들러
  const handleChange = (e: { target: { name: string; value: string } }) => {
    const { name, value } = e.target;
    const updatedQuestions = [...questions];
    if (name === "content") {
      updatedQuestions[currentQuestionIndex].content = value;
      setQuestions(updatedQuestions);
    }
  };

  // 슬라이드 이동 문제 해결
  const handleNextQuestion = () => {
    // 다음 질문이 없을 경우 새로운 질문을 추가하고 index를 증가시킴
    if (currentQuestionIndex === questions.length - 1 && questions.length < 5) {
      setQuestions([
        ...questions,
        {
          content: "",
          answers: [
            { content: "", correct: false },
            { content: "", correct: false },
          ],
          memberId: questions[0].memberId,
        },
      ]);
    }
    // 인덱스 증가 - 새 질문이 없으면 단순히 index 증가
    setCurrentQuestionIndex((prevIndex) => prevIndex + 1);
  };

  const handlePreviousQuestion = () => {
    // 이전 질문으로 이동
    if (currentQuestionIndex > 0) {
      setCurrentQuestionIndex((prevIndex) => prevIndex - 1);
    }
  };

  const mutation = useMutation({
    mutationFn: createQuestion,
    onSuccess: () => {
      alert("질문 생성 성공");
      router.push("/");
    },
    onError: (error) => {
      console.error("질문 생성 실패...", error);
    },
  });

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    mutation.mutate(questions);
  };

  const isFirstPage = currentQuestionIndex === 0;
  const isLastPage = currentQuestionIndex === 4;

  return (
    <div className="p-4 w-full max-w-lg mx-auto">
      <h1 className="text-3xl font-bold mb-6 text-center text-white-700">나에 대한 질문 만들기</h1>

      {/* 페이지 인덱스 표시 */}
      <div className="mb-4 text-center text-lg font-bold">
        {currentQuestionIndex + 1} / 5
      </div>

      {/* 슬라이드 컨테이너 */}
      <div className="relative w-full overflow-hidden">
        {/* 카드들을 감싸는 div */}
        <div
          className="flex transition-transform duration-300 ease-in-out"
          style={{ transform: `translateX(-${currentQuestionIndex * 100}%)` }}
        >
          {questions.map((_, index) => (
            <div key={index} className="w-full shrink-0 px-4 bg-white p-4 rounded-lg">
              <form onSubmit={handleSubmit} className="space-y-6">
                {/* 질문 입력 필드 */}
                <div className="flex flex-col items-center">
                  <input
                    type="text"
                    id="content"
                    name="content"
                    value={questions[currentQuestionIndex].content}
                    onChange={handleChange}
                    placeholder={`질문 ${currentQuestionIndex + 1} 내용을 입력하세요`}
                    className="border border-purple-300 p-4 rounded-lg w-full h-16 text-lg text-black"
                    required
                  />
                </div>

                {questions[currentQuestionIndex].answers.map((answer, index) => (
                  <AnswerOption
                    key={index}
                    index={index}
                    answerContent={answer.content}
                    correct={answer.correct}
                    onAnswerChange={handleAnswerChange}
                    onCorrectChange={() => handleCorrectChange(index)}
                  />
                ))}

                {questions[currentQuestionIndex].answers.length < 4 && (
                  <div className="text-center">
                    <button
                      type="button"
                      onClick={handleAddAnswer}
                      className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 transition mt-4"
                    >
                      +
                    </button>
                  </div>
                )}

                {/* 이전 및 다음 버튼 */}
                <div className="flex justify-between mt-6">
                  <button
                    type="button"
                    onClick={handlePreviousQuestion}
                    className={classNames(
                      "px-4 py-2 rounded-lg transition",
                      isFirstPage ? "bg-gray-300 text-gray-500 cursor-not-allowed" : "bg-[#6f2cdb] text-white hover:bg-[#550ec2] transition"
                    )}
                    disabled={isFirstPage}
                  >
                    이전
                  </button>
                  <button
                    type="button"
                    onClick={handleNextQuestion}
                    className={classNames(
                      "px-4 py-2 rounded-lg transition",
                      isLastPage ? "bg-gray-300 text-gray-500 cursor-not-allowed" : "bg-[#6f2cdb] text-white hover:bg-[#550ec2] transition"
                    )}
                    disabled={isLastPage}
                  >
                    다음
                  </button>
                </div>

                {/* 제출 버튼 */}
                <div className="mt-6 text-center">
                  <button
                    type="submit"
                    className={classNames(
                      "font-bold py-3 px-6 rounded-lg shadow-md transition duration-300 w-full",
                      isLastPage ? "bg-green-500 hover:bg-green-600 text-white" : "bg-gray-300 text-gray-500 cursor-not-allowed"
                    )}
                    disabled={!isLastPage} // 마지막 페이지에서만 활성화
                  >
                    생성
                  </button>
                </div>
              </form>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
