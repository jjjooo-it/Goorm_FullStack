import { useState, useEffect } from "react";
import { Question } from "@/app/questions/member/[id]/page";
import { QuestionTabs } from "./QuestionTabs";
import { useRouter } from "next/navigation"; // Import useRouter

export const Tabs = ({ questions }: { questions: Question[] }) => {
  const [activeTab, setActiveTab] = useState(0); // 현재 활성화된 탭의 index
  const [selectedAnswers, setSelectedAnswers] = useState<number[]>([]); // 사용자의 선택한 답변 저장
  const [randomQuestions, setRandomQuestions] = useState<Question[]>([]); // 무작위로 선택된 5개 질문
  const router = useRouter(); // Initialize the router

  // 컴포넌트가 마운트될 때 5개의 무작위 질문을 선택
  useEffect(() => {
    const shuffledQuestions = questions.sort(() => 0.5 - Math.random());
    const selectedQuestions = shuffledQuestions.slice(0, 5); // 5개의 질문 선택
    setRandomQuestions(selectedQuestions);
  }, [questions]);

  // 다음 탭으로 넘어가는 함수
  const handleNext = () => {
    if (activeTab < randomQuestions.length - 1) {
      setActiveTab(activeTab + 1);
    }
  };

  // 이전 탭으로 돌아가는 함수
  const handlePrevious = () => {
    if (activeTab > 0) {
      setActiveTab(activeTab - 1);
    }
  };

  // 마지막 탭에서 제출 버튼을 누르면 호출될 함수
  const handleSubmit = () => {
    let score = 0;

    // 사용자가 선택한 답변과 정답을 비교하여 점수를 계산
    randomQuestions.forEach((question, index) => {
      const selectedAnswerIndex = selectedAnswers[index]; // 사용자가 선택한 답변의 인덱스
      const correctAnswer = question.answers.find((answer) => answer.correct); // 정답인 답변을 찾음
      if (
        correctAnswer &&
        question.answers[selectedAnswerIndex] === correctAnswer
      ) {
        score++; // 정답이면 score 증가
      }
    });

    // 점수를 Result 페이지로 전달
    router.push(`/result?score=${score}`);
  };

  // 답변 선택 핸들러
  const handleAnswerSelect = (answerIndex: number) => {
    const updatedAnswers = [...selectedAnswers];
    updatedAnswers[activeTab] = answerIndex; // 현재 활성화된 질문의 선택지를 저장
    setSelectedAnswers(updatedAnswers);
  };

  return (
    <div className="w-full max-w-lg mx-auto">
      {/* 탭 목록 */}
      <div className="flex space-x-2">
        {randomQuestions.map((question, index: number) => (
          <button
            key={index}
            className={`px-4 py-2 rounded-t-lg ${
              index === activeTab
                ? "bg-[#BCC6F5] text-[#2C2C2C]"
                : "bg-[#A5A9C7] text-gray-600 hover:bg-[#E6F0FF]"
            }`}
            onClick={() => setActiveTab(index)}
          >
            문제 {index + 1}
          </button>
        ))}
      </div>

      {/* 활성화된 질문 표시 */}
      <div className="bg-white shadow-lg rounded-b-lg overflow-hidden">
        {randomQuestions.length > 0 && (
          <QuestionTabs
            question={randomQuestions[activeTab]}
            onAnswerSelect={handleAnswerSelect}
            selectedAnswer={selectedAnswers[activeTab]}
          />
        )}
      </div>

      {/* 다음 버튼과 제출 버튼 */}
      <div className="mt-4 flex justify-end">
        {/* 이전 버튼 */}
        <button
          className={`px-4 py-2 rounded-lg ${
            activeTab === 0
              ? "bg-gray-300 text-gray-500 cursor-not-allowed mr-2"
              : "bg-[#6f2cdb] text-white mr-2 hover:bg-[#550ec2] transition"
          }`}
          onClick={handlePrevious}
          disabled={activeTab === 0} // 첫 번째 탭일 경우 비활성화
        >
          &lt;
        </button>
        {activeTab < randomQuestions.length - 1 ? (
          <button
            className="bg-[#6f2cdb] text-white px-4 py-2 rounded-lg hover:bg-[#550ec2] transition"
            onClick={handleNext}
          >
            &gt;
          </button>
        ) : (
          <button
            className="bg-[#28A745] text-white px-4 py-2 rounded-lg hover:bg-[#218838] transition"
            onClick={handleSubmit}
          >
            결과 확인
          </button>
        )}
      </div>
    </div>
  );
};
