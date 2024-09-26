import Link from "next/link";

interface MyButtonProps {
  content: string;
  route?: string;
  type?: "button" | "submit" | "reset" | undefined;
}

export function MyButton({ content, route, type }: MyButtonProps) {
  return (
    <>
      {route && (
        <Link
          href={route!}
          className="bg-[#6f2cdb] text-white rounded-lg px-8 py-4 text-2xl hover:bg-[#550ec2] transition"
        >
          {content}
        </Link>
      )}
      {type && (
        <button
          type={type}
          className="bg-[#6f2cdb] text-white rounded-lg px-8 py-4 text-2xl ml-2 hover:bg-[#550ec2] transition"
        >
          {content}
        </button>
      )}
    </>
  );
}
