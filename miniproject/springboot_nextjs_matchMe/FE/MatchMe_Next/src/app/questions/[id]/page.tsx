import { MyButton } from "@/components/MyButton";
import { Selection } from "@/components/Selection";

export default function Question() {
  return (
    <div>
      <h1>1/5</h1>
      <Selection selectOne="따뜻한 냉면" selectTwo="차가운 라면" />
      <MyButton content="다음" route="/questions/:id" />
    </div>
  );
}
