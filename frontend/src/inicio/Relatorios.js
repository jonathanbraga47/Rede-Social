import { useEffect, useState } from "react";
import {
    BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip,
    ResponsiveContainer, Cell, PieChart, Pie, Legend,
} from "recharts";
import "./Relatorios.css";

// ── URLs reais do seu backend ─────────────────────────────────────
const URL_RANKING    = "http://localhost:8080/viewRankingUsuarios/ranking";
const URL_INATIVIDADE = "http://localhost:8080/viewPerfisInativos/inatividade";
const URL_ENGAJAMENTO = "http://localhost:8080/viewEngajamento/engajamento";

const STATUS_COLOR = {
    "Totalmente Inativo":           "#ff4d4d",
    "Nunca Publicou nem Interagiu": "#ff8c42",
    "Nunca Publicou":               "#f0b429",
    "Nunca Interagiu":              "#7d5fff",
    "Nao Segue Ninguem":            "#5f4bcc",
};

const PURPLE       = "#7d5fff";
const PURPLE_DARK  = "#5f4bcc";
const PURPLE_MID   = "#6a4df0";
const PURPLE_LIGHT = "#9b7fff";
const PURPLE_PALE  = "#e0d9ff";

const TABS = ["Ranking de Usuários", "Perfis Inativos", "Engajamento de Posts"];

function CustomTooltip({ active, payload, label }) {
    if (!active || !payload?.length) return null;
    return (
        <div className="rel-tooltip">
            <p className="rel-tooltip-label">{label}</p>
            {payload.map((p, i) => (
                <p key={i} className="rel-tooltip-value">
                    {p.name}: <strong>{p.value}</strong>
                </p>
            ))}
        </div>
    );
}

function Badge({ status }) {
    const color = STATUS_COLOR[status] || "#888";
    return (
        <span
            className="rel-badge"
            style={{ background: `${color}18`, color, border: `1px solid ${color}44` }}
        >
            {status}
        </span>
    );
}

function Relatorios() {
    const [tab, setTab]                 = useState(0);
    const [ranking, setRanking]         = useState([]);
    const [inatividade, setInatividade] = useState([]);
    const [engajamento, setEngajamento] = useState([]);
    const [loading, setLoading]         = useState(true);
    const [erro, setErro]               = useState(false);

    useEffect(() => {
        setLoading(true);
        setErro(false);

        Promise.all([
            fetch(URL_RANKING).then(r => r.json()),
            fetch(URL_INATIVIDADE).then(r => r.json()),
            fetch(URL_ENGAJAMENTO).then(r => r.json()),
        ])
            .then(([r, i, e]) => {
                console.log("Engajamento:", e);
                setRanking(Array.isArray(r) ? r : []);
                setInatividade(Array.isArray(i) ? i : []);
                setEngajamento(Array.isArray(e) ? e : []);
            })
            .catch(err => {
                console.error(err);
                setErro(true);
            })
            .finally(() => setLoading(false));
    }, []);

    const topUser       = ranking[0];
    const totalInativos = inatividade.length;
    const totalAtivos   = ranking.filter(u => u.scoreEngajamento > 0).length;
    const postTop       = engajamento[0];

    const pieData = Object.entries(
        inatividade.reduce((acc, u) => {
            acc[u.statusAtividade] = (acc[u.statusAtividade] || 0) + 1;
            return acc;
        }, {})
    ).map(([name, value]) => ({ name, value }));

    return (
        <div className="table-card">

            <div className="table-header">
                <h2 className="table-title">Relatórios</h2>
            </div>

            {erro && (
                <p className="rel-erro">
                    Não foi possível carregar os dados. Verifique se o backend está rodando.
                </p>
            )}

            {/* KPI Cards */}
            {!loading && !erro && (
                <div className="rel-kpi-grid">
                    <div className="rel-kpi-card">
                        <span className="rel-kpi-label">Usuário Destaque</span>
                        <span className="rel-kpi-value">{topUser?.nome?.split(" ")[0] ?? "—"}</span>
                        <span className="rel-kpi-sub">Score: {topUser?.scoreEngajamento ?? 0}</span>
                    </div>
                    <div className="rel-kpi-card">
                        <span className="rel-kpi-label">Usuários Ativos</span>
                        <span className="rel-kpi-value">{totalAtivos}</span>
                        <span className="rel-kpi-sub">com atividade registrada</span>
                    </div>
                    <div className="rel-kpi-card">
                        <span className="rel-kpi-label">Perfis Inativos</span>
                        <span className="rel-kpi-value">{totalInativos}</span>
                        <span className="rel-kpi-sub">sem engajamento</span>
                    </div>
                    <div className="rel-kpi-card">
                        <span className="rel-kpi-label">Post em Alta</span>
                        <span className="rel-kpi-value">#{postTop?.idPublicacao ?? "—"}</span>
                        <span className="rel-kpi-sub">{postTop?.totalInteracoes ?? 0} interações</span>
                    </div>
                </div>
            )}

            {/* Tabs */}
            <div className="rel-tabs">
                {TABS.map((t, i) => (
                    <button
                        key={t}
                        className={`rel-tab-btn ${tab === i ? "rel-tab-active" : ""}`}
                        onClick={() => setTab(i)}
                    >
                        {t}
                    </button>
                ))}
            </div>

            {loading ? (
                <p className="rel-loading">Carregando dados...</p>
            ) : erro ? null : (
                <div className="rel-content">

                    {/* ── RANKING ── */}
                    {tab === 0 && (
                        <>
                            <h3 className="rel-section-title">Score de Engajamento</h3>
                            <div className="rel-chart-box">
                                <ResponsiveContainer width="100%" height={280}>
                                    <BarChart data={ranking.slice(0, 8)} margin={{ top: 10, right: 20, left: 0, bottom: 0 }}>
                                        <CartesianGrid strokeDasharray="3 3" stroke="#f0f0f0" />
                                        <XAxis dataKey="nome" tick={{ fill: "#999", fontSize: 12 }} tickFormatter={v => v.split(" ")[0]} />
                                        <YAxis tick={{ fill: "#999", fontSize: 12 }} />
                                        <Tooltip content={<CustomTooltip />} />
                                        <Bar dataKey="scoreEngajamento" name="Score" radius={[8, 8, 0, 0]}>
                                            {ranking.slice(0, 8).map((_, i) => (
                                                <Cell key={i} fill={i === 0 ? PURPLE : i === 1 ? PURPLE_LIGHT : i === 2 ? "#bba4ff" : PURPLE_PALE} />
                                            ))}
                                        </Bar>
                                    </BarChart>
                                </ResponsiveContainer>
                            </div>

                            <div className="rel-chart-grid">
                                <div className="rel-chart-box">
                                    <p className="rel-chart-label">Curtidas vs Comentários Recebidos</p>
                                    <ResponsiveContainer width="100%" height={220}>
                                        <BarChart data={ranking.slice(0, 5)}>
                                            <CartesianGrid strokeDasharray="3 3" stroke="#f0f0f0" />
                                            <XAxis dataKey="nome" tick={{ fill: "#999", fontSize: 11 }} tickFormatter={v => v.split(" ")[0]} />
                                            <YAxis tick={{ fill: "#999", fontSize: 11 }} />
                                            <Tooltip content={<CustomTooltip />} />
                                            <Legend wrapperStyle={{ fontSize: 12 }} />
                                            <Bar dataKey="curtidasRecebidas"    name="Curtidas"    fill={PURPLE}      radius={[4, 4, 0, 0]} />
                                            <Bar dataKey="comentariosRecebidos" name="Comentários" fill={PURPLE_DARK}  radius={[4, 4, 0, 0]} />
                                        </BarChart>
                                    </ResponsiveContainer>
                                </div>
                                <div className="rel-chart-box">
                                    <p className="rel-chart-label">Seguidores vs Seguindo</p>
                                    <ResponsiveContainer width="100%" height={220}>
                                        <BarChart data={ranking.slice(0, 5)}>
                                            <CartesianGrid strokeDasharray="3 3" stroke="#f0f0f0" />
                                            <XAxis dataKey="nome" tick={{ fill: "#999", fontSize: 11 }} tickFormatter={v => v.split(" ")[0]} />
                                            <YAxis tick={{ fill: "#999", fontSize: 11 }} />
                                            <Tooltip content={<CustomTooltip />} />
                                            <Legend wrapperStyle={{ fontSize: 12 }} />
                                            <Bar dataKey="totalSeguidores" name="Seguidores" fill={PURPLE}     radius={[4, 4, 0, 0]} />
                                            <Bar dataKey="totalSeguindo"   name="Seguindo"   fill={PURPLE_MID} radius={[4, 4, 0, 0]} />
                                        </BarChart>
                                    </ResponsiveContainer>
                                </div>
                            </div>

                            <h3 className="rel-section-title">Tabela Completa</h3>
                            <div className="table-container">
                                <table>
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Nome</th>
                                        <th>Email</th>
                                        <th>Publicações</th>
                                        <th>Curtidas</th>
                                        <th>Comentários</th>
                                        <th>Seguidores</th>
                                        <th>Score</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {ranking.map((u, i) => (
                                        <tr key={u.idPerfil}>
                                            <td className={i < 3 ? "rel-rank-top" : "rel-rank-normal"}>{i + 1}</td>
                                            <td><strong>{u.nome}</strong></td>
                                            <td className="rel-muted">{u.email}</td>
                                            <td>{u.totalPublicacoes}</td>
                                            <td>{u.curtidasRecebidas}</td>
                                            <td>{u.comentariosRecebidos}</td>
                                            <td>{u.totalSeguidores}</td>
                                            <td className="rel-score">{u.scoreEngajamento}</td>
                                        </tr>
                                    ))}
                                    </tbody>
                                </table>
                            </div>
                        </>
                    )}

                    {/* ── INATIVIDADE ── */}
                    {tab === 1 && (
                        <>
                            <h3 className="rel-section-title">Distribuição de Inatividade</h3>
                            <div className="rel-chart-grid">
                                <div className="rel-chart-box">
                                    <p className="rel-chart-label">Por Tipo de Status</p>
                                    <ResponsiveContainer width="100%" height={300}>
                                        <PieChart margin={{ top: 30, right: 80, bottom: 30, left: 80 }}>
                                            <Pie
                                                data={pieData} cx="50%" cy="45%" outerRadius={110}
                                                dataKey="value" nameKey="name"
                                                label={({ percent }) => `${(percent * 100).toFixed(0)}%`}
                                                labelLine={true}
                                            >
                                                {pieData.map((entry, i) => (
                                                    <Cell key={i} fill={STATUS_COLOR[entry.name] || PURPLE} />
                                                ))}
                                            </Pie>
                                            <Tooltip content={<CustomTooltip />} />
                                            <Legend wrapperStyle={{ fontSize: 11 }} />
                                        </PieChart>
                                    </ResponsiveContainer>
                                </div>
                                <div className="rel-chart-box">
                                    <p className="rel-chart-label">Atividade dos Perfis Inativos</p>
                                    <ResponsiveContainer width="100%" height={260}>
                                        <BarChart data={inatividade.slice(0, 6)}>
                                            <CartesianGrid strokeDasharray="3 3" stroke="#f0f0f0" />
                                            <XAxis dataKey="nome" tick={{ fill: "#999", fontSize: 10 }} tickFormatter={v => v.split(" ")[0]} />
                                            <YAxis tick={{ fill: "#999", fontSize: 10 }} />
                                            <Tooltip content={<CustomTooltip />} />
                                            <Legend wrapperStyle={{ fontSize: 11 }} />
                                            <Bar dataKey="totalPublicacoes" name="Posts"      fill={PURPLE}      radius={[4, 4, 0, 0]} />
                                            <Bar dataKey="totalInteracoes"  name="Interações" fill={PURPLE_DARK} radius={[4, 4, 0, 0]} />
                                            <Bar dataKey="totalSeguidores"  name="Seguidores" fill="#bba4ff"     radius={[4, 4, 0, 0]} />
                                        </BarChart>
                                    </ResponsiveContainer>
                                </div>
                            </div>

                            <h3 className="rel-section-title">Tabela de Perfis Inativos</h3>
                            <div className="table-container">
                                <table>
                                    <thead>
                                    <tr>
                                        <th>Nome</th>
                                        <th>Email</th>
                                        <th>Posts</th>
                                        <th>Interações</th>
                                        <th>Seguidores</th>
                                        <th>Seguindo</th>
                                        <th>Status</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {inatividade.map((u, i) => (
                                        <tr key={i}>
                                            <td><strong>{u.nome}</strong></td>
                                            <td className="rel-muted">{u.email}</td>
                                            <td>{u.totalPublicacoes}</td>
                                            <td>{u.totalInteracoes}</td>
                                            <td>{u.totalSeguidores}</td>
                                            <td>{u.totalSeguindo}</td>
                                            <td><Badge status={u.statusAtividade} /></td>
                                        </tr>
                                    ))}
                                    </tbody>
                                </table>
                            </div>
                        </>
                    )}

                    {/* ── ENGAJAMENTO ── */}
                    {tab === 2 && (
                        <>
                            <h3 className="rel-section-title">Posts com Maior Engajamento</h3>
                            <div className="rel-chart-box">
                                <ResponsiveContainer width="100%" height={Math.max(280, engajamento.length * 40)}>
                                    <BarChart data={engajamento} layout="vertical" margin={{ top: 10, right: 30, left: 10, bottom: 10 }}>
                                        <CartesianGrid strokeDasharray="3 3" stroke="#f0f0f0" horizontal={false} />
                                        <XAxis type="number" tick={{ fill: "#999", fontSize: 12 }} />
                                        <YAxis type="category" dataKey="idPublicacao" tick={{ fill: "#999", fontSize: 12 }}
                                               tickFormatter={v => `#${v}`} width={40} />
                                        <Tooltip content={<CustomTooltip />} />
                                        <Bar dataKey="totalInteracoes" name="Interações" radius={[0, 8, 8, 0]} barSize={20}>
                                            {engajamento.map((_, i) => (
                                                <Cell key={i} fill={i === 0 ? PURPLE : i === 1 ? PURPLE_LIGHT : PURPLE_PALE} />
                                            ))}
                                        </Bar>
                                    </BarChart>
                                </ResponsiveContainer>
                            </div>

                            <h3 className="rel-section-title">Tabela de Publicações</h3>
                            <div className="table-container">
                                <table>
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Autor</th>
                                        <th>Legenda</th>
                                        <th>Data</th>
                                        <th>Interações</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {engajamento.map((p, i) => (
                                        <tr key={p.idPublicacao}>
                                            <td className="rel-muted">#{p.idPublicacao}</td>
                                            <td><strong>{p.autor}</strong></td>
                                            <td>{p.legenda}</td>
                                            <td className="rel-muted">
                                                {p.dataHora ? new Date(p.dataHora).toLocaleDateString("pt-BR") : "—"}
                                            </td>
                                            <td className={i === 0 ? "rel-score" : ""}>{p.totalInteracoes}</td>
                                        </tr>
                                    ))}
                                    </tbody>
                                </table>
                            </div>
                        </>
                    )}

                </div>
            )}
        </div>
    );
}

export default Relatorios;