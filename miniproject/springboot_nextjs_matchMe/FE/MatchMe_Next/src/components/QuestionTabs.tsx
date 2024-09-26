import { Question } from "@/app/questions/member/[id]/page";

interface QuestionTabsProps {
  question: Question;
  onAnswerSelect: (answerIndex: number) => void;
  selectedAnswer: number | undefined;
}

export const QuestionTabs = ({ question, onAnswerSelect, selectedAnswer }: QuestionTabsProps) => {
  return (
    <div className="p-4 border border-gray-200 rounded-md shadow-sm">
      <div className="bg-[#C0C7DF] text-white text-center py-4 flex items-center justify-center">
        <h2 className="text-lg font-semibold text-[#313492]">
          {question.content}
        </h2>
      </div>
      <ul className="p-6 bg-[#F0F4FF] space-y-4">
        {question.answers.map((answer, index) => (
          <li
            key={index}
            className={`w-full text-[#313492] border border-gray-300 rounded-lg py-3 transition duration-300 cursor-pointer ${
              selectedAnswer === index ? "bg-[#FFD37C]" : "bg-gray-200 hover:bg-[#FFE29B]"
            }`}
            onClick={() => onAnswerSelect(index)}
          >
            {answer.content}
          </li>
        ))}
      </ul>
    </div>
  );
};
